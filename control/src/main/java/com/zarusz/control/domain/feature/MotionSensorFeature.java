package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("sensor_motion")
public class MotionSensorFeature extends DeviceFeature {

    private Boolean movement;

    protected MotionSensorFeature() {
    }

    public MotionSensorFeature(Device device, Feature feature) {
        super(device, feature);
    }

}
