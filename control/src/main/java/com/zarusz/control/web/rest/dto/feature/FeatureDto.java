package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.FeatureType;
import com.zarusz.control.web.rest.dto.feature.FeatureStateDto;
import lombok.Data;

/**
 * Created by Tomasz on 9/21/2015.
 */
@Data
public class FeatureDto extends FeatureWithIdStateDto {

    private String displayName;
    private String displayIcon;
    private int displayPriority;

    public FeatureDto() {
    }

    public FeatureDto(DeviceFeature deviceFeature) {
        super(deviceFeature);

        this.displayName = deviceFeature.getDisplayName() != null ? deviceFeature.getDisplayName() : deviceFeature.getFeature().getDisplayName();
        this.displayIcon = deviceFeature.getDisplayIcon() != null ? deviceFeature.getDisplayIcon() : deviceFeature.getFeature().getDisplayIcon();
        this.displayPriority = deviceFeature.getDisplayPriority();
    }
}
