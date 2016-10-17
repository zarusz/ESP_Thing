package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.app.comm.messages.MessageReceivedEvent;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.feature.HumiditySensorFeature;
import com.zarusz.control.domain.feature.TemperatureSensorFeature;
import com.zarusz.control.repository.DeviceRepository;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Tomasz on 4/17/2016.
 */
@Component
public class DeviceStatusResponseHandler extends AbstractHandler {

    @Inject
    private DeviceRepository deviceRepo;

    @Inject
    public DeviceStatusResponseHandler(MBassador bus) {
        super(bus, LoggerFactory.getLogger(DeviceStatusResponseHandler.class));
    }

    @Handler
    public void onResponse(DeviceMessageProtos.DeviceStatusResponse e) {

        log.debug("Device {} status is {}", e.getDeviceId(), e.getMessage());

        /*
        if (!(e.getMessage() instanceof DeviceMessageProtos.DeviceEvents)) {
            return;
        }

        if (e.getMessage().hasTemperatureMeasureEvent()) {
            DeviceMessageProtos.DeviceTemperatureMeasuredEvent t = e.getMessage().getTemperatureMeasureEvent();
            log.debug("The temperature is {} C.", t.getValue());

            HubDevice device = deviceRepo.findByGuid(t.getDeviceId());
            if (device != null) {
                device.onReportActivity();
                TemperatureSensorFeature f = (TemperatureSensorFeature) device.getFeatureByPort(t.getPort());
                if (f != null) {
                    f.updateValue(t.getValue());
                }
            }
        }

        if (e.getMessage().hasHumidityMeasureEvent()) {
            DeviceMessageProtos.DeviceHumidityMeasuredEvent h = e.getMessage().getHumidityMeasureEvent();
            log.debug("The humidity is {} %.", h.getValue());
            HubDevice device = deviceRepo.findByGuid(h.getDeviceId());
            if (device != null) {
                device.onReportActivity();
                HumiditySensorFeature f = (HumiditySensorFeature) device.getFeatureByPort(h.getPort());
                if (f != null) {
                    f.updateValue(h.getValue());
                }
            }
        }
        */
    }
}
