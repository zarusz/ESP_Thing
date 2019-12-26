package com.zarusz.control.web.websocket.dto;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.msg.events.FeatureStateChangedEvent;
import com.zarusz.control.repository.DeviceRepository;
import com.zarusz.control.web.rest.DeviceResource;
import com.zarusz.control.web.rest.dto.DeviceStatusDto;
import com.zarusz.control.web.rest.dto.feature.FeatureWithIdStateDto;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import javax.inject.Inject;

/**
 * Created by Tomasz on 5/7/2016.
 */
public class DeviceStatusNotifier extends AbstractHandler {

    @Inject
    private SimpMessageSendingOperations messagingTemplate;

    @Inject
    private DeviceRepository deviceRepository;


    public DeviceStatusNotifier(MBassador bus) {
        super(bus, LoggerFactory.getLogger(DeviceStatusNotifier.class));
    }

    @Handler
    public void onDeviceStatus(DeviceMessageProtos.DeviceStatusResponse e) {
        HubDevice device = deviceRepository.findByGuid(e.getDeviceId());
        DeviceStatusDto statusDto = new DeviceStatusDto(device);
        statusDto.setStatus(e.getMessage());

        messagingTemplate.convertAndSend("/topic/device-status", statusDto);
    }
}
