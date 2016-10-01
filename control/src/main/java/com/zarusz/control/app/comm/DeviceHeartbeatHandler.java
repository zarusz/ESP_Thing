package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.TopicHandler;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.repository.DeviceRepository;
import net.engio.mbassy.bus.MBassador;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by Tomasz on 4/10/2016.
 */
public class DeviceHeartbeatHandler extends TopicHandler<DeviceMessageProtos.DeviceEvents> {

    public DeviceHeartbeatHandler(MBassador bus) {

        super(bus, LoggerFactory.getLogger(DeviceHeartbeatHandler.class), Topics.DeviceEvents);
    }

    @Inject
    private DeviceRepository deviceRepository;

    @Override
    public void handle(String topic, DeviceMessageProtos.DeviceEvents msg) {

        if (msg.hasDeviceHearbeatEvent()) {
            HubDevice device = deviceRepository.findByGuid(msg.getDeviceHearbeatEvent().getDeviceId());
            if (device != null) {
                device.onReportActivity();
                log.debug("Received DeviceHearbeatEvent for device {} with sequence {}.", msg.getDeviceHearbeatEvent().getDeviceId(), msg.getDeviceHearbeatEvent().getSequenceId());
            }
        }
        if (msg.hasDeviceConnectedEvent()) {
            log.debug("Received DeviceConnectedEvent for device {}.", msg.getDeviceConnectedEvent().getDeviceId());
        }
        if (msg.hasDeviceDisconnectedEvent()) {
            log.debug("Received DeviceDisconnectedEvent for device {}.", msg.getDeviceConnectedEvent().getDeviceId());
        }
    }
}
