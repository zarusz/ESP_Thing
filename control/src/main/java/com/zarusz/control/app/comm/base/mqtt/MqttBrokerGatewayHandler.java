package com.zarusz.control.app.comm.base.mqtt;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.app.comm.messages.MessageReceivedEvent;
import com.zarusz.control.app.comm.messages.PublishMessageCommand;
import com.zarusz.control.app.comm.Topics;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

/**
 * Created by Tomasz on 4/9/2016.
 */
public class MqttBrokerGatewayHandler extends AbstractHandler implements Runnable {

    private final Logger log = LoggerFactory.getLogger(MqttBrokerGatewayHandler.class);

    @Value("${control.mqtt.client_id}")
    private String mqttClientId;
    private final MQTT mqttClient;
    private CallbackConnection mqttConnection;
    private boolean mqttConnected;
    private Thread thread;
    private boolean threadRun = true;

    private final ConcurrentLinkedQueue<PublishMessageCommand> publishQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<MessageReceivedEvent> dispatchQueue = new ConcurrentLinkedQueue<>();

    private final String[] subscribedTopics = new String[]{
        Topics.DeviceDescription,
        Topics.DeviceEvents
    };

    @Inject
    private PlatformTransactionManager txManager;

    public MqttBrokerGatewayHandler(MBassador bus, MQTT mqttClient) throws Exception {
        super(bus, LoggerFactory.getLogger(MqttBrokerGatewayHandler.class));

        this.mqttClient = mqttClient;
        this.mqttConnected = false;

        setupConnection();

        start();
    }

    private void start() throws Exception {
        this.connect();
        this.thread = new Thread(this);
        this.thread.start();
    }

    private void setupConnection() {
        mqttClient.setClientId(mqttClientId + "_" + Long.toHexString(System.currentTimeMillis()));

        mqttConnection = mqttClient.callbackConnection();
        mqttConnection.listener(new Listener() {

            public void onDisconnected() {
                log.info("MQTT disconnected");
            }

            public void onConnected() {
                log.info("MQTT connected");

                final Topic[] topics = Stream.of(subscribedTopics)
                    .map(x -> new Topic(x, QoS.AT_LEAST_ONCE))
                    .toArray(Topic[]::new);

                mqttConnection.subscribe(topics, null);
            }

            @Override
            public void onPublish(UTF8Buffer topic, Buffer payload, Runnable ack) {
                // You can now process a received message from a topic.

                fromMQTT(payload.toByteArray(), topic.toString());
                // Once process execute the ack runnable.
                ack.run();
            }

            public void onFailure(Throwable value) {
                mqttConnection.disconnect(null);
            }
        });
    }

    private void connect() {
        mqttConnection.connect(new Callback<Void>() {

            // Once we connect..
            public void onSuccess(Void v) {
                mqttConnected = true;
                /*
                // Send a message to a topic
                mqttConnection.publish("foo", "Hello".getBytes(), QoS.AT_LEAST_ONCE, false, new Callback<Void>() {
                    public void onSuccess(Void v) {
                        // the pubish operation completed successfully.
                    }
                    public void onFailure(Throwable value) {
                        connection.close(null); // publish failed.
                    }
                });
                */
            }

            public void onFailure(Throwable value) {
                //result.failure(value); // If we could not connect to the server.
                log.error("Could not connect.", value);
                mqttConnected = false;
            }
        });
    }

    private void disconnect() {
        /*
        try {
            mqttConnection.unsubscribe(Stream.of(subscribedTopics).map(x -> Buffer.utf8(x)).collect(), null);
        } catch (Exception e) {
        }
        */

        mqttConnection.disconnect(new Callback<Void>() {
            public void onSuccess(Void v) {
                mqttConnected = false;
                // called once the connection is disconnected.
                log.info("Disconnected");
            }

            public void onFailure(Throwable e) {
                log.warn("Could not disconnect", e);
            }
        });
    }

    @Override
    public void close() throws IOException {
        threadRun = false;

        disconnect();
        super.close();
    }

    private void fromMQTT(byte[] payload, String topic) {
        log.debug("Received message of size {} on topic {}", payload.length, topic);
        try {
            MessageLite typedMsg = deserializeMessage(topic, payload);
            if (typedMsg != null) {
                dispatchQueue.add(new MessageReceivedEvent(topic, typedMsg));
            }
        } catch (Exception e) {
            log.error("Could not receive a message from topic {}.", topic, e);
        }
    }

    protected boolean toMQTT(PublishMessageCommand cmd) {
        try {
            byte[] payload = serializeMessage(cmd);
            mqttConnection.getDispatchQueue().execute(() -> mqttConnection.publish(cmd.getTopic(), payload, QoS.AT_LEAST_ONCE, false, new Callback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    log.debug("Published message of size {} to topic {}", payload.length, cmd.getTopic());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    log.error("Could not publish message to topic {}", cmd.getTopic(), throwable);
                }
            }));
        } catch (Exception e) {
            log.error("Could not publish message to topic {}.", cmd.getTopic(), e);
            return false;
        }
        return true;
    }

    private byte[] serializeMessage(PublishMessageCommand cmd) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
        try {
            cmd.getMessage().writeTo(outputStream);
            return outputStream.toByteArray();
        } finally {
            outputStream.close();
        }
    }

    protected <T extends MessageLite> T deserializeMessage(String topic, byte[] payload) throws InvalidProtocolBufferException {
        T typedMsg = null;
        switch (topic) {
            case Topics.DeviceDescription:
                typedMsg = (T) DeviceMessageProtos.DeviceDescription.parseFrom(payload);
                break;
            case Topics.DeviceEvents:
                typedMsg = (T) DeviceMessageProtos.DeviceEvents.parseFrom(payload);
                break;
            default:
                log.warn("Unsupported topic {}", topic);
        }
        return typedMsg;
    }

    @Handler
    public void handlePublishMessageCommand(PublishMessageCommand cmd) {
        publishQueue.add(cmd);
    }

    @Override
    public void run() {
        while (threadRun) {
            if (!runCycle()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
        }
        thread = null;
    }

    private boolean runCycle() {
        if (!mqttConnected) {
            log.warn("Not connected to MQTT.");
            return false;
        }

        boolean activityPerformed = false;

        // Publish all local commands into MQTT.
        PublishMessageCommand publishCmd;
        while ((publishCmd = publishQueue.poll()) != null) {
            if (toMQTT(publishCmd)) {
                activityPerformed = true;
            }
        }

        // Dispatch messaged locally
        MessageReceivedEvent receivedEvent;
        while ((receivedEvent = dispatchQueue.poll()) != null) {
            dispatchMessageLocally(receivedEvent);
            activityPerformed = true;
        }

        return activityPerformed;
    }

    private boolean dispatchMessageLocally(MessageReceivedEvent receivedEvent) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = txManager.getTransaction(def);
        try {
            // execute your business logic here
            bus.publish(receivedEvent);
            txManager.commit(status);
            return true;
        } catch (Exception e) {
            txManager.rollback(status);
            log.error("Could not dispatch message.", e);
            return false;
        }
    }
}
