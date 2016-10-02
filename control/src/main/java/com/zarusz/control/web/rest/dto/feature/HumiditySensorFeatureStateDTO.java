package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.HumiditySensorFeature;
import lombok.Data;

/**
 * Created by Tomasz on 4/21/2016.
 */
@Data
public class HumiditySensorFeatureStateDTO extends FeatureStateDto {

    private float humidity;

    public HumiditySensorFeatureStateDTO() {
    }

    public HumiditySensorFeatureStateDTO(HumiditySensorFeature feature) {
        super(feature);
        humidity = feature.getHumidity();
    }

    @Override
    public void handle(DeviceFeature feature) {

    }
}
