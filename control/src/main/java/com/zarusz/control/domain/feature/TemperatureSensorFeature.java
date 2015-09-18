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
@DiscriminatorValue("sensor_temp")
public class TemperatureSensorFeature extends DeviceFeature {

    //private float value;

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

