package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.HumiditySensorFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 4/21/2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HumiditySensorFeatureStateDto extends FeatureStateDto {

    private float humidity;

    public HumiditySensorFeatureStateDto() {
    }

    public HumiditySensorFeatureStateDto(HumiditySensorFeature feature) {
        super(feature);
        humidity = feature.getHumidity();
    }

    @Override
    public void handle(DeviceFeature feature) {

    }
}
