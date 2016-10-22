package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.app.comm.messages.PublishMessageCommand;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.msg.commands.IRCommand;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.stream.Collectors;

import static com.zarusz.control.device.messages.DeviceMessageProtos.*;

public class IRFeatureHandler extends AbstractHandler {

    @Inject
    private Topics topics;

    public IRFeatureHandler(MBassador bus) throws Exception {
        super(bus, LoggerFactory.getLogger(IRFeatureHandler.class));
    }

    @Handler
    public void onCommand(IRCommand cmd) {
        // TODO: send to device
        try {
            HubDevice hubDevice;
            if (cmd.getDevice() instanceof HubDevice) {
                hubDevice = (HubDevice) cmd.getDevice();
            } else {
                hubDevice = cmd.getDevice().getHub();
            }
            log.debug("IR to device {} port {} with value {}.", hubDevice.getGuid(), cmd.getDeviceFeature().getPort(), cmd.getSignal());

            IRSignal irSignal = IRSignal
                .newBuilder()
                .setFormat(Mapper.map(cmd.getSignal().getFormat()))
                .addAllBytes(cmd.getSignal().getBytes().stream().map(x -> IRSignalByte.newBuilder().setBits(x.getBits()).setData(x.getData()).build()).collect(Collectors.toList()))
                .build();

            DeviceIRCommand irCommand = DeviceIRCommand
                .newBuilder()
                .setMessageId(1234)
                .setPort(cmd.getDeviceFeature().getPort())
                .setSignal(irSignal)
                .build();

            DeviceMessage deviceMessage = DeviceMessage
                .newBuilder()
                .setIrCommand(irCommand)
                .build();

            bus.publish(new PublishMessageCommand(topics.getDeviceTopic(hubDevice), deviceMessage));
        } catch (Exception e) {
            log.error("Cannot publish the message.", e);
        }
    }
}

