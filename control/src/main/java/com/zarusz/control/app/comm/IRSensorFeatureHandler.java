package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.app.comm.messages.MessageReceivedEvent;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.feature.IRSensorFeature;
import com.zarusz.control.repository.DeviceRepository;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by Tomasz on 4/17/2016.
 */
public class IRSensorFeatureHandler extends AbstractHandler {

    @Inject
    private DeviceRepository deviceRepo;

    public IRSensorFeatureHandler(MBassador bus) {
        super(bus, LoggerFactory.getLogger(IRSensorFeatureHandler.class));
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
        log.debug("Received IR signal on port {} with data {}, bits {}, format {}.", ir.getPort(), ir.getValue().getData(), ir.getValue().getBits(), ir.getValue().getFormat());

        HubDevice device = deviceRepo.findByGuid(ir.getDeviceId());
        if (device != null) {
            device.onReportActivity();
            IRSensorFeature irIn = (IRSensorFeature) device.getFeatureByPort(ir.getPort());
            if (irIn != null) {
                //irIn.updateValue(t.getValue());
            }
        }
    }
}
