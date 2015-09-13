package com.zarusz.control.domain.features;

import com.zarusz.control.domain.topology.Device;
import com.zarusz.control.domain.topology.DeviceFeature;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("light_sensor")
public class LightSensorFeature extends DeviceFeature {

    //private float value;

    protected LightSensorFeature() {
    }

    public LightSensorFeature(Device device, Feature feature) {
        super(device, feature);
    }

    public void measureTemperature() {
        // TODO
    }

	//public float getMeasuredTemperature() {
    //    return value;
    //}

}

