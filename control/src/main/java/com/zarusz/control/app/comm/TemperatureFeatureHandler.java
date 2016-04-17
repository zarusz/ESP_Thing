package com.zarusz.control.app.comm;

import com.zarusz.control.device.messages.DeviceMessageProtos;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

/**
 * Created by Tomasz on 4/17/2016.
 */
public class TemperatureFeatureHandler extends AbstractHandler {
    public TemperatureFeatureHandler(MBassador bus) {
        super(bus, LoggerFactory.getLogger(TemperatureFeatureHandler.class));
    }

    @Handler
    public void onDeviceEvent(MessageReceivedEvent<DeviceMessageProtos.DeviceEvents> e) {

        if (!(e.getMessage() instanceof DeviceMessageProtos.DeviceEvents)) {
            return;
        }

        if (e.getMessage().hasTemperatureMeasureEvent()) {
            log.info("The temperature is {} C.", e.getMessage().getTemperatureMeasureEvent().getValue());
        }
        if (e.getMessage().hasHumidityMeasureEvent()) {
            log.info("The humidity is {} %.", e.getMessage().getHumidityMeasureEvent().getValue());
        }
    }
}
