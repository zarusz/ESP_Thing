package com.zarusz.control.web.rest.dto.feature;

import com.zarusz.control.domain.device.DeviceFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 10/12/2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeatureWithIdStateDto extends FeatureWithIdDto {

    private FeatureStateDto state;

    public FeatureWithIdStateDto() {
    }

    public FeatureWithIdStateDto(DeviceFeature deviceFeature) {
        super(deviceFeature);
        this.state = FeatureStateDto.create(deviceFeature);
    }
}

