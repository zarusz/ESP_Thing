package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.Feature;
import com.zarusz.control.web.rest.dto.feature.FeatureStateDTO;
import lombok.Data;

/**
 * Created by Tomasz on 9/21/2015.
 */
@Data
public class FeatureDTO {

    private Integer id;
    private String type;
    private String displayName;
    private String displayIcon;
    private Integer displayPriority;
    private FeatureStateDTO state;

    public FeatureDTO() {
    }

    public FeatureDTO(DeviceFeature deviceFeature) {
        this.id = deviceFeature.getId();
        this.type = deviceFeature.getFeature().getFeature();
        this.displayName = deviceFeature.getDisplayName() != null ? deviceFeature.getDisplayName() : deviceFeature.getFeature().getDisplayName();
        this.displayIcon = deviceFeature.getDisplayIcon() != null ? deviceFeature.getDisplayIcon() : deviceFeature.getFeature().getDisplayIcon();
        this.displayPriority = deviceFeature.getFeature().getDisplayPriority();
        this.state = FeatureStateDTO.create(deviceFeature);
    }
}
