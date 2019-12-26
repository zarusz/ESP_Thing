package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.app.comm.messages.PublishMessageCommand;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.msg.commands.UpgradeFirmwareCommand;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by Tomasz on 10/2/2016.
 */
public class ServiceCommandHandler extends AbstractHandler {

    @Inject
    private Topics topics;

    public ServiceCommandHandler(MBassador bus) throws Exception {
        super(bus, LoggerFactory.getLogger(ServiceCommandHandler.class));
    }

    @Handler
    public void onCommand(UpgradeFirmwareCommand cmd) {
        try {
            log.debug("Upgrade firmware command for device {} with url {}.", cmd.getDevice().getGuid(), cmd.getFirmwareUrl());

            DeviceMessageProtos.UpgradeFirmwareCommand.Builder upgradeFirmwareCommand = DeviceMessageProtos.UpgradeFirmwareCommand.newBuilder();
            upgradeFirmwareCommand.setProgramUrl(cmd.getFirmwareUrl());

            DeviceMessageProtos.DeviceServiceCommand.Builder deviceServiceCommand = DeviceMessageProtos.DeviceServiceCommand.newBuilder();
            deviceServiceCommand.setUpgradeFirmwareCommand(upgradeFirmwareCommand);

            final String topic = topics.getDeviceServiceTopic(cmd.getDevice());
            bus.publish(new PublishMessageCommand(topic, deviceServiceCommand.build()));
        } catch (Exception e) {
            log.error("Cannot publish the message.", e);
        }
    }
}
