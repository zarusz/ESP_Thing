package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.ir.IRFeature;
import com.zarusz.control.domain.feature.ir.IRSignal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 10/3/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IRFeatureStateDto extends FeatureStateDto {

    private IRSignal signal;

    public IRFeatureStateDto() {
    }

    public IRFeatureStateDto(IRFeature feature) {
    }

    @Override
    public void handle(DeviceFeature feature) {
        IRFeature irFeature = (IRFeature) feature;
        irFeature.send(signal);
    }
}

