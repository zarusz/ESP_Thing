package com.zarusz.control.app.comm;

import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.feature.HumiditySensorFeature;
import com.zarusz.control.domain.feature.IRInFeature;
import com.zarusz.control.domain.feature.TemperatureSensorFeature;
import com.zarusz.control.repository.DeviceRepository;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by Tomasz on 4/17/2016.
 */
public class IRReceiverFeatureHandler extends AbstractHandler {

    @Inject
    private DeviceRepository deviceRepo;

    public IRReceiverFeatureHandler(MBassador bus) {
        super(bus, LoggerFactory.getLogger(IRReceiverFeatureHandler.class));
    }

    @Handler
    public void onDeviceEvent(MessageReceivedEvent<DeviceMessageProtos.DeviceEvents> e) {

        if (!(e.getMessage() instanceof DeviceMessageProtos.DeviceEvents)) {
            return;
        }

        if (!e.getMessage().hasIrReceivedEvent()) {
            return;
        }
        DeviceMessageProtos.DeviceIRReceivedEvent ir = e.getMessage().getIrReceivedEvent();
        log.info("Received IR signal on port {} with value {}.", ir.getPort(), ir.getValue());

        HubDevice device = deviceRepo.findHubByGuid(ir.getDeviceId());
        if (device != null) {
            IRInFeature irIn = (IRInFeature) device.getFeatureByPort(ir.getPort());
            if (irIn != null) {
                //irIn.updateValue(t.getValue());
            }
        }
    }
}
