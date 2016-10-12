package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.IRFeature;
import lombok.Data;

/**
 * Created by Tomasz on 10/3/2015.
 */
@Data
public class IRFeatureStateDto extends FeatureStateDto {

    private int value;

    public IRFeatureStateDto() {
    }

    public IRFeatureStateDto(IRFeature feature) {
    }

    @Override
    public void handle(DeviceFeature feature) {
        IRFeature irOutFeature = (IRFeature) feature;
        irOutFeature.send(value);
    }
}

