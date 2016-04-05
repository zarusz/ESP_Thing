package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.SwitchFeature;
import lombok.Data;

/**
 * Created by Tomasz on 9/27/2015.
 */
@Data
public class SwitchFeatureStateDTO extends FeatureStateDTO {

    private boolean on;

    public SwitchFeatureStateDTO() {

    }

    public SwitchFeatureStateDTO(SwitchFeature feature) {
        super(feature);
        this.on = feature.isOn();
    }

    @Override
    public void handle(DeviceFeature feature) {
        SwitchFeature switchFeature = (SwitchFeature) feature;
        switchFeature.setOn(on);
    }
}
