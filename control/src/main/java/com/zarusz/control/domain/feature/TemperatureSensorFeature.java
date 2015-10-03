package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("sensor_temp")
public class TemperatureSensorFeature extends DeviceFeature {

    private float temperature;

    protected TemperatureSensorFeature() {
    }

    public TemperatureSensorFeature(Device device, Feature feature) {
        super(device, feature);
    }

    public void measureTemperature() {
        // TODO
    }

	//public float getMeasuredTemperature() {
    //    return value;
    //}

}

