package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.MotionSensorFeature;

/**
 * Created by Tomasz on 10/1/2016.
 */
public class MotionSensorFeatureStateDTO extends FeatureStateDTO {

    public MotionSensorFeatureStateDTO() {
    }

    public MotionSensorFeatureStateDTO(MotionSensorFeature feature) {
        super(feature);
    }


    @Override
    public void handle(DeviceFeature feature) {

    }
}
