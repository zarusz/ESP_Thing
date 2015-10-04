package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.feature.LEDFeature;
import lombok.Data;

/**
 * Created by Tomasz on 10/3/2015.
 */
@Data
public class LEDFeatureStateDTO extends FeatureStateDTO {

    private int mode;

    public LEDFeatureStateDTO() {
    }

    public LEDFeatureStateDTO(LEDFeature feature) {
        mode = feature.getState();
    }
}

