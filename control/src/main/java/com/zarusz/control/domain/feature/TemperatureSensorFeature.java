package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.common.EventBus;
import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.msg.events.HumidityChangedEvent;
import com.zarusz.control.domain.msg.events.TemperatureChangedEvent;
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

    public void updateValue(float value) {
        float oldValue = temperature;

        setTemperature(value);
        // TODO record history

        if (oldValue != temperature) {
            EventBus.current().publish(new TemperatureChangedEvent(this, oldValue));
        }
    }

    //public float getMeasuredTemperature() {
    //    return value;
    //}

}

