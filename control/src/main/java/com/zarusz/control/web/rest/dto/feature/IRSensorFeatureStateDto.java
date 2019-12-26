package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.ir.IRSensorFeature;

/**
 * Created by Tomasz on 10/1/2016.
 */
public class IRSensorFeatureStateDto extends FeatureStateDto {

    public IRSensorFeatureStateDto() {
    }

    public IRSensorFeatureStateDto(IRSensorFeature feature) {
        super(feature);
    }

    @Override
    public void handle(DeviceFeature feature) {

    }
}