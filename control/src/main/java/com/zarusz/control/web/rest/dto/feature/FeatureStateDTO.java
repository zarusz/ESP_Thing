package com.zarusz.control.web.rest.dto.feature;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.*;
import lombok.Data;

import java.util.Date;

/**
 * Created by Tomasz on 9/27/2015.
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(SwitchFeatureStateDTO.class),
    @JsonSubTypes.Type(IRFeatureStateDTO.class),
    @JsonSubTypes.Type(IRSensorFeatureStateDTO.class),
    @JsonSubTypes.Type(TemperatureSensorFeatureStateDTO.class),
    @JsonSubTypes.Type(HumiditySensorFeatureStateDTO.class),
    @JsonSubTypes.Type(MotionSensorFeatureStateDTO.class)
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
        if (feature instanceof IRFeature) {
            return new IRFeatureStateDTO((IRFeature) feature);
        }
        if (feature instanceof IRSensorFeature) {
            return new IRSensorFeatureStateDTO((IRSensorFeature) feature);
        }
        if (feature instanceof TemperatureSensorFeature) {
            return new TemperatureSensorFeatureStateDTO((TemperatureSensorFeature) feature);
        }
        if (feature instanceof HumiditySensorFeature) {
            return new HumiditySensorFeatureStateDTO((HumiditySensorFeature) feature);
        }
        if (feature instanceof MotionSensorFeature) {
            return new MotionSensorFeatureStateDTO((MotionSensorFeature) feature);
        }
        throw new RuntimeException("Feature type not supported.");
    }

    public abstract void handle(DeviceFeature feature);
}
