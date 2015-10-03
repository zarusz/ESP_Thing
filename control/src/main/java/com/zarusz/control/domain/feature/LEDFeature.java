package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("led")
public class LEDFeature extends DeviceFeature {

    private int state;

    protected LEDFeature() {
    }

    public LEDFeature(Device device, Feature feature) {
        super(device, feature);
    }

}
