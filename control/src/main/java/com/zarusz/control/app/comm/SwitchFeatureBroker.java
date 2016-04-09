package com.zarusz.control.app.comm;

import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.msg.commands.SwitchCommand;
import com.zarusz.control.domain.msg.commands.TargetingDeviceCommand;
import lombok.Data;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Data
public class SwitchFeatureBroker extends BaseGatewayBroker {

    private final MQTT mqttClient;
    private final BlockingConnection mqttConnection;

    public SwitchFeatureBroker(MBassador bus, MQTT mqttClient) throws Exception {
        super(bus, LoggerFactory.getLogger(SwitchFeatureBroker.class));
        this.mqttClient = mqttClient;
        this.mqttConnection = mqttClient.blockingConnection();
        this.mqttConnection.connect();
    }

    @Override
    public void close() throws IOException {
        try {
            this.mqttConnection.disconnect();
        } catch (Exception e) {
        }
        super.close();
    }

    @Handler
    public void handleDeviceCommand(TargetingDeviceCommand cmd) {
        // TODO: send to device
        log.debug("Generic device command received.");
    }

    @Handler
    public void handleSwitch(SwitchCommand cmd) {
        // TODO: send to device
        log.debug("Switch to {} on device feature {}.", cmd.isOn() ? "on" : "off", cmd.getDeviceFeature().getId());
        try {
            HubDevice hubDevice;
            if (cmd.getDevice() instanceof HubDevice) {
                hubDevice = (HubDevice) cmd.getDevice();
            } else {
                hubDevice = cmd.getDevice().getHub();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);

            String topic = "device/" + hubDevice.getGuid();
            String msg = "Hello from hub! Switch: " + cmd.getDeviceFeature().getPort() + ", on: " + cmd.isOn();


            DeviceMessageProtos.DeviceSwitchCommand.Builder switchCommand = DeviceMessageProtos.DeviceSwitchCommand.newBuilder();
            switchCommand.setMessageId(1234);
            switchCommand.setPort(1);
            switchCommand.setOn(cmd.isOn());

            DeviceMessageProtos.DeviceMessage.Builder deviceMessage = DeviceMessageProtos.DeviceMessage.newBuilder();
            deviceMessage.setSwitchCommand(switchCommand);

            deviceMessage.build().writeTo(outputStream);

            mqttConnection.publish(topic, outputStream.toByteArray(), QoS.AT_LEAST_ONCE, false);
        } catch (Exception e) {
            log.error("Cannot publish on the MQTT client.", e);
        }
    }
}
