package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.IRFeature;
import lombok.Data;

/**
 * Created by Tomasz on 10/3/2015.
 */
@Data
public class IRFeatureStateDTO extends FeatureStateDto {

    private int value;

    public IRFeatureStateDTO() {
    }

    public IRFeatureStateDTO(IRFeature feature) {
    }

    @Override
    public void handle(DeviceFeature feature) {
        IRFeature irOutFeature = (IRFeature) feature;
        irOutFeature.send(value);
    }
}

