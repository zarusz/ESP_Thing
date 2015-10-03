package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("color")
public class ColorFeature extends DeviceFeature {

    private int red;
    private int green;
    private int blue;

    protected ColorFeature() {
    }

    public ColorFeature(Device device, Feature feature) {
        super(device, feature);
    }

}
