package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.messages.MessageReceivedEvent;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

/**
 * Created by Tomasz on 4/10/2016.
 */
public class DeviceHeartbeatHandler  extends AbstractHandler {

    public DeviceHeartbeatHandler(MBassador bus) {
        super(bus, LoggerFactory.getLogger(DeviceHeartbeatHandler.class));
    }

    @Handler
    public void onDeviceEvents(MessageReceivedEvent<DeviceMessageProtos.DeviceEvents> event) {
        if (event.getTopic() != Topics.DeviceEvents) {
            return;
        }

        DeviceMessageProtos.DeviceEvents msg = event.getMessage();
        if (msg.hasDeviceHearbeatEvent()) {
            log.debug("Received DeviceHearbeatEvent for device {} with sequence {}.", msg.getDeviceHearbeatEvent().getDeviceId(), msg.getDeviceHearbeatEvent().getSequenceId());
        }
        if (msg.hasDeviceConnectedEvent()) {
            log.debug("Received DeviceConnectedEvent for device {}.", msg.getDeviceConnectedEvent().getDeviceId());
        }
        if (msg.hasDeviceDisconnectedEvent()) {
            log.debug("Received DeviceDisconnectedEvent for device {}.", msg.getDeviceConnectedEvent().getDeviceId());
        }
    }
}
