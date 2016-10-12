package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.TemperatureSensorFeature;
import lombok.Data;

/**
 * Created by Tomasz on 4/21/2016.
 */
@Data
public class TemperatureSensorFeatureStateDto extends FeatureStateDto {

    private float temperature;

    public TemperatureSensorFeatureStateDto() {
    }

    public TemperatureSensorFeatureStateDto(TemperatureSensorFeature feature) {
        super(feature);
        temperature = feature.getTemperature();
    }

    @Override
    public void handle(DeviceFeature feature) {
    }
}
