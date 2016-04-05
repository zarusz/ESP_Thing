package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.DimFeature;
import lombok.Data;

/**
 * Created by Tomasz on 9/27/2015.
 */
@Data
public class DimFeatureStateDTO extends FeatureStateDTO {

    private float intensity;

    public DimFeatureStateDTO() {
    }

    public DimFeatureStateDTO(DimFeature feature) {
        super(feature);
        intensity = feature.getIntensity();
    }

    @Override
    public void handle(DeviceFeature feature) {
    }
}
