package com.zarusz.control.app.comm.mqtt;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.zarusz.control.app.comm.AbstractHandler;
import com.zarusz.control.app.comm.messages.MessageReceivedEvent;
import com.zarusz.control.app.comm.messages.PublishMessageCommand;
import com.zarusz.control.app.comm.Topics;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Created by Tomasz on 4/9/2016.
 */
public class MqttBrokerGatewayHandler extends AbstractHandler implements Runnable {

    private final Logger log = LoggerFactory.getLogger(MqttBrokerGatewayHandler.class);
    private final MQTT mqttClient;
    private BlockingConnection mqttConnection;
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
        start();
    }

    private void start() throws Exception {
        this.mqttConnection = mqttClient.blockingConnection();
        this.mqttConnection.connect();

        final Topic[] topics = Stream.of(subscribedTopics).map(x -> new Topic(x, QoS.AT_LEAST_ONCE)).toArray(Topic[]::new);
        this.mqttConnection.subscribe(topics);

        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void close() throws IOException {
        threadRun = false;

        if (mqttConnection != null) {
            try {
                mqttConnection.unsubscribe(subscribedTopics);
            } catch (Exception e) {
            }

            try {
                mqttConnection.disconnect();
            } catch (Exception e) {
            }
            mqttConnection = null;
        }
        super.close();
    }

    protected boolean publishMessageToMQTT(PublishMessageCommand cmd) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
        try {
            cmd.getMessage().writeTo(outputStream);
        } catch (IOException e) {
            log.error("Could not serialize message.", e);
            return false;
        }

        try {
            mqttConnection.publish(cmd.getTopic(), outputStream.toByteArray(), QoS.AT_LEAST_ONCE, false);
        } catch (Exception e) {
            log.error("Could not publish message to topic {}.", cmd.getTopic(), e);
            return false;
        }
        return true;
    }

    protected boolean readMessageFromMQTT() {
        try {
            org.fusesource.mqtt.client.Message msg = mqttConnection.receive(10, TimeUnit.MILLISECONDS);
            if (msg != null) {
                MessageLite typedMsg = deserializeMessage(msg.getTopic(), msg.getPayload());
                if (typedMsg != null) {
                    dispatchQueue.add(new MessageReceivedEvent(msg.getTopic(), typedMsg));
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Could not receive a message.", e);
        }
        return false;
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
        if (mqttConnection == null) {
            return false;
        }

        if (!mqttConnection.isConnected()) {
            log.warn("Not connected to MQTT.");
            return false;
        }

        boolean activityPerformed = false;

        // Publish all local commands into MQTT.
        PublishMessageCommand publishCmd;
        while ((publishCmd = publishQueue.peek()) != null) {
            if (publishMessageToMQTT(publishCmd)) {
                activityPerformed = true;
                publishQueue.poll();
            }
        }

        // Retrieve messages from MQTT into local queue
        if (readMessageFromMQTT()) {
            activityPerformed = true;
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
