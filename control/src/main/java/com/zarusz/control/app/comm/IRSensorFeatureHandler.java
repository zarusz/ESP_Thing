package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.AbstractHandler;
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
    public void onIR(DeviceMessageProtos.DeviceIRSignalEvent e) {
        log.debug("Received IR signal on port {} with signal {}", e.getPort(), e.getSignal());

        HubDevice device = deviceRepo.findByGuid(e.getDeviceId());
        if (device != null) {
            device.onReportActivity();
            IRSensorFeature irSensor = (IRSensorFeature) device.getFeatureByPort(e.getPort());
            if (irSensor != null) {
                //irSensor.updateValue(t.getValue());
            }
        }
    }
}
