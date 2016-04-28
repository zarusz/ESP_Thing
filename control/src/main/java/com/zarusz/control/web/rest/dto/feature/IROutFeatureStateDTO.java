package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.IROutFeature;
import com.zarusz.control.domain.feature.LEDFeature;
import com.zarusz.control.domain.feature.SwitchFeature;
import lombok.Data;

/**
 * Created by Tomasz on 10/3/2015.
 */
@Data
public class IROutFeatureStateDTO extends FeatureStateDTO {

    private int value;

    public IROutFeatureStateDTO() {
    }

    public IROutFeatureStateDTO(IROutFeature feature) {
    }

    @Override
    public void handle(DeviceFeature feature) {
        IROutFeature irOutFeature = (IROutFeature) feature;
        irOutFeature.send(value);
    }
}

