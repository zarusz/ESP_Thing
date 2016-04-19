package com.zarusz.control.app.comm;

import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.msg.commands.SwitchCommand;
import com.zarusz.control.domain.msg.commands.TargetingDeviceCommand;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

public class SwitchFeatureHandler extends AbstractHandler {

    public SwitchFeatureHandler(MBassador bus) throws Exception {
        super(bus, LoggerFactory.getLogger(SwitchFeatureHandler.class));
    }

    @Handler
    public void onDeviceCommand(TargetingDeviceCommand cmd) {
        // TODO: send to device
        log.debug("Generic device command received.");
    }

    @Handler
    public void onSwitchCommand(SwitchCommand cmd) {
        // TODO: send to device
        try {
            HubDevice hubDevice;
            if (cmd.getDevice() instanceof HubDevice) {
                hubDevice = (HubDevice) cmd.getDevice();
            } else {
                hubDevice = cmd.getDevice().getHub();
            }
            log.debug("Switch to {} on device {} port {}.", cmd.isOn() ? "on" : "off", hubDevice.getGuid(), cmd.getDeviceFeature().getPort());

            DeviceMessageProtos.DeviceSwitchCommand.Builder switchCommand = DeviceMessageProtos.DeviceSwitchCommand.newBuilder();
            switchCommand.setMessageId(1234);
            switchCommand.setPort(cmd.getDeviceFeature().getPort());
            switchCommand.setOn(cmd.isOn());

            DeviceMessageProtos.DeviceMessage.Builder deviceMessage = DeviceMessageProtos.DeviceMessage.newBuilder();
            deviceMessage.setSwitchCommand(switchCommand);

            bus.publish(new PublishMessageCommand(Topics.getDeviceTopic(hubDevice), deviceMessage.build()));
        } catch (Exception e) {
            log.error("Cannot publish the message.", e);
        }
    }
}
