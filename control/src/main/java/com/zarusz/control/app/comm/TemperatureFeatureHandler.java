package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.feature.HumiditySensorFeature;
import com.zarusz.control.domain.feature.TemperatureSensorFeature;
import com.zarusz.control.repository.DeviceRepository;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by Tomasz on 4/17/2016.
 */
public class TemperatureFeatureHandler extends AbstractHandler {

    @Inject
    private DeviceRepository deviceRepo;

    public TemperatureFeatureHandler(MBassador bus) {
        super(bus, LoggerFactory.getLogger(TemperatureFeatureHandler.class));
    }

    @Handler
    public void onTemperatureReading(DeviceMessageProtos.DeviceTemperatureMeasuredEvent t) {
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

    @Handler
    public void onHumidityReading(DeviceMessageProtos.DeviceHumidityMeasuredEvent h) {
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
}