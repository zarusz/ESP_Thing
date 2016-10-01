package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Tomasz on 4/23/2016.
 */
@Entity
@DiscriminatorValue("SensorIR")
public class IRSensorFeature extends DeviceFeature {

    protected IRSensorFeature() {
    }

    public IRSensorFeature(Device device, Feature feature, int port) {
        super(device, feature, port);
    }

}
