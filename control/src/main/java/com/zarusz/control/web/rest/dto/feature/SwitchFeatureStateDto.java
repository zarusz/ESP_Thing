package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.SwitchFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 9/27/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SwitchFeatureStateDto extends FeatureStateDto {

    private boolean on;

    public SwitchFeatureStateDto() {

    }

    public SwitchFeatureStateDto(SwitchFeature feature) {
        super(feature);
        this.on = feature.isOn();
    }

    @Override
    public void handle(DeviceFeature feature) {
        SwitchFeature switchFeature = (SwitchFeature) feature;
        switchFeature.setOn(on);
    }
}
