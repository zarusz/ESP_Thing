package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true, of = {})
@ToString(of = {"id", "port"})
@Entity
@DiscriminatorValue("color")
public class ColorFeature extends DeviceFeature {

    protected ColorFeature() {
    }

    public ColorFeature(Device device, Feature feature) {
        super(device, feature);
    }

}
