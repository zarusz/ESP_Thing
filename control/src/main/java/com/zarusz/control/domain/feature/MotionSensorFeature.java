package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("sensor_motion")
public class MotionSensorFeature extends DeviceFeature {

    protected MotionSensorFeature() {
    }

    public MotionSensorFeature(Device device, Feature feature) {
        super(device, feature);
    }

}
