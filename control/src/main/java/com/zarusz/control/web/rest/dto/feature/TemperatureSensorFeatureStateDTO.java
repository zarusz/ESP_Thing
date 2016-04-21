package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.TemperatureSensorFeature;
import lombok.Data;

/**
 * Created by Tomasz on 4/21/2016.
 */
@Data
public class TemperatureSensorFeatureStateDTO extends FeatureStateDTO {

    private float temperature;

    public TemperatureSensorFeatureStateDTO() {
    }

    public TemperatureSensorFeatureStateDTO(TemperatureSensorFeature feature) {
        super(feature);
        temperature = feature.getTemperature();
    }

    @Override
    public void handle(DeviceFeature feature) {
    }
}
