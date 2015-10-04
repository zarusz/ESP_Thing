package com.zarusz.control.web.rest.dto.feature;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.DimFeature;
import com.zarusz.control.domain.feature.LEDFeature;
import com.zarusz.control.domain.feature.SwitchFeature;
import lombok.Data;

import java.util.Date;

/**
 * Created by Tomasz on 9/27/2015.
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(SwitchFeatureStateDTO.class),
        @JsonSubTypes.Type(DimFeatureStateDTO.class),
        @JsonSubTypes.Type(LEDFeatureStateDTO.class)
})
public abstract class FeatureStateDTO {

    private Date updated;

    protected FeatureStateDTO() {

    }

    protected FeatureStateDTO(DeviceFeature feature) {
        updated = feature.getUpdated();
    }

    public static FeatureStateDTO create(DeviceFeature feature) {
        if (feature instanceof SwitchFeature) {
            return new SwitchFeatureStateDTO((SwitchFeature) feature);
        }
        if (feature instanceof DimFeature) {
            return new DimFeatureStateDTO((DimFeature) feature);
        }
        if (feature instanceof LEDFeature) {
            return new LEDFeatureStateDTO((LEDFeature) feature);
        }
        return null;
    }

}
