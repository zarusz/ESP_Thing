package com.zarusz.control.app.comm;

import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.msg.commands.IRCommand;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

public class IRTransceiverFeatureHandler extends AbstractHandler {

    public IRTransceiverFeatureHandler(MBassador bus) throws Exception {
        super(bus, LoggerFactory.getLogger(IRTransceiverFeatureHandler.class));
    }

    @Handler
    public void onIRCommand(IRCommand cmd) {
        // TODO: send to device
        try {
            HubDevice hubDevice;
            if (cmd.getDevice() instanceof HubDevice) {
                hubDevice = (HubDevice) cmd.getDevice();
            } else {
                hubDevice = cmd.getDevice().getHub();
            }
            log.debug("IR to device {} port {} with value {}.", hubDevice.getGuid(), cmd.getDeviceFeature().getPort(), cmd.getValue());

            DeviceMessageProtos.IRValue.Builder irValue = DeviceMessageProtos.IRValue.newBuilder();
            irValue.setBits(32);
            irValue.setFormat(DeviceMessageProtos.IRFormat.NEC);
            irValue.setData(cmd.getValue());

            DeviceMessageProtos.DeviceIRSendCommand.Builder irSendCommand = DeviceMessageProtos.DeviceIRSendCommand.newBuilder();
            irSendCommand.setMessageId(1234);
            irSendCommand.setPort(cmd.getDeviceFeature().getPort());
            irSendCommand.setValue(irValue);

            DeviceMessageProtos.DeviceMessage.Builder deviceMessage = DeviceMessageProtos.DeviceMessage.newBuilder();
            deviceMessage.setIrSendCommand(irSendCommand);

            bus.publish(new PublishMessageCommand(Topics.getDeviceTopic(hubDevice), deviceMessage.build()));
        } catch (Exception e) {
            log.error("Cannot publish the message.", e);
        }
    }
}
