package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.MotionSensorFeature;

/**
 * Created by Tomasz on 10/1/2016.
 */
public class MotionSensorFeatureStateDto extends FeatureStateDto {

    public MotionSensorFeatureStateDto() {
    }

    public MotionSensorFeatureStateDto(MotionSensorFeature feature) {
        super(feature);
    }


    @Override
    public void handle(DeviceFeature feature) {

    }
}
