package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.FeatureType;
import com.zarusz.control.web.rest.dto.feature.FeatureStateDto;
import lombok.Data;

/**
 * Created by Tomasz on 9/21/2015.
 */
@Data
public class FeatureDTO {

    private Integer id;
    private FeatureType type;
    private String displayName;
    private String displayIcon;
    private int displayPriority;
    private FeatureStateDto state;

    public FeatureDTO() {
    }

    public FeatureDTO(DeviceFeature deviceFeature) {
        this.id = deviceFeature.getId();
        this.type = deviceFeature.getFeature().getFeature();
        this.displayName = deviceFeature.getDisplayName() != null ? deviceFeature.getDisplayName() : deviceFeature.getFeature().getDisplayName();
        this.displayIcon = deviceFeature.getDisplayIcon() != null ? deviceFeature.getDisplayIcon() : deviceFeature.getFeature().getDisplayIcon();
        this.displayPriority = deviceFeature.getDisplayPriority();
        this.state = FeatureStateDto.create(deviceFeature);
    }
}
