package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.FeatureType;
import lombok.Data;

@Data
public class FeatureWithIdDto {
    private int id;
    private FeatureType type;

    public FeatureWithIdDto() {
    }

    public FeatureWithIdDto(DeviceFeature deviceFeature) {
        this.id = deviceFeature.getId();
        this.type = deviceFeature.getFeature().getFeature();
    }
}
