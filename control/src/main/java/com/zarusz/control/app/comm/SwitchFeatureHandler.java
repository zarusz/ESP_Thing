package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.app.comm.messages.PublishMessageCommand;
import static com.zarusz.control.device.messages.DeviceMessageProtos.*;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.msg.commands.SwitchCommand;
import com.zarusz.control.domain.msg.commands.TargetingDeviceCommand;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class SwitchFeatureHandler extends AbstractHandler {

    @Inject
    private Topics topics;

    public SwitchFeatureHandler(MBassador bus) throws Exception {
        super(bus, LoggerFactory.getLogger(SwitchFeatureHandler.class));
    }

    @Handler
    public void onDeviceCommand(TargetingDeviceCommand cmd) {
        // TODO: send to device
        log.debug("Generic device command received.");
    }

    @Handler
    public void onCommand(SwitchCommand cmd) {
        // TODO: send to device
        try {
            HubDevice hubDevice;
            if (cmd.getDevice() instanceof HubDevice) {
                hubDevice = (HubDevice) cmd.getDevice();
            } else {
                hubDevice = cmd.getDevice().getHub();
            }
            log.debug("Switch to {} on device {} port {}", cmd.isOn() ? "on" : "off", hubDevice.getGuid(), cmd.getDeviceFeature().getPort());

            DeviceSwitchCommand switchCommand = DeviceSwitchCommand
                .newBuilder()
                .setMessageId(1234)
                .setPort(cmd.getDeviceFeature().getPort())
                .setOn(cmd.isOn())
                .build();

            DeviceMessage deviceMessage = DeviceMessage
                .newBuilder()
                .setSwitchCommand(switchCommand)
                .build();

            bus.publish(new PublishMessageCommand(topics.getDeviceTopic(hubDevice), deviceMessage));
        } catch (Exception e) {
            log.error("Cannot publish the message.", e);
        }
    }
}