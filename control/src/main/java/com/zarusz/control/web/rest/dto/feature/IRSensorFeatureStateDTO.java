package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.IRSensorFeature;

/**
 * Created by Tomasz on 10/1/2016.
 */
public class IRSensorFeatureStateDTO extends FeatureStateDTO {

    public IRSensorFeatureStateDTO() {
    }

    public IRSensorFeatureStateDTO(IRSensorFeature feature) {
        super(feature);
    }

    @Override
    public void handle(DeviceFeature feature) {

    }
}
