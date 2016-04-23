package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Tomasz on 4/23/2016.
 */
@Entity
@DiscriminatorValue("ir_in")
public class IRInFeature extends DeviceFeature {

    protected IRInFeature() {
    }

    public IRInFeature(Device device, Feature feature) {
        super(device, feature);
    }

}
