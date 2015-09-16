package com.zarusz.control.domain.msg.events;

import com.zarusz.control.domain.feature.TemperatureSensorFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TemperatureChangedEvent extends SensorChangedEvent<TemperatureSensorFeature> {

    private float temperature;

    public TemperatureChangedEvent() {
    }

    public TemperatureChangedEvent(TemperatureSensorFeature feature, float temperature) {
        super(feature);
        this.temperature = temperature;
    }
}
