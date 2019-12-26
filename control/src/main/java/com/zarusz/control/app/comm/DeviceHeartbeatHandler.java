package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.app.comm.base.TopicHandler;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.repository.DeviceRepository;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by Tomasz on 4/10/2016.
 */
public class DeviceHeartbeatHandler extends AbstractHandler {

    public DeviceHeartbeatHandler(MBassador bus) {
        super(bus, LoggerFactory.getLogger(DeviceHeartbeatHandler.class));
    }

    @Inject
    private DeviceRepository deviceRepository;

    @Handler
    public void onDeviceHeartbeat(DeviceMessageProtos.DeviceHearbeatEvent e) {

        HubDevice device = deviceRepository.findByGuid(e.getDeviceId());
        if (device != null) {
            device.onReportActivity();
            log.debug("Received DeviceHeartbeatEvent for device {} with sequence {}.", e.getDeviceId(), e.getSequenceId());
        }
    }

    @Handler
    public void onDeviceConnected(DeviceMessageProtos.DeviceConnectedEvent e) {
        log.debug("Received DeviceConnectedEvent for device {}.", e.getDeviceId());
    }

    @Handler
    public void onDeviceDisconnected(DeviceMessageProtos.DeviceConnectedEvent e) {
        log.debug("Received DeviceDisconnectedEvent for device {}.", e.getDeviceId());
    }
}
