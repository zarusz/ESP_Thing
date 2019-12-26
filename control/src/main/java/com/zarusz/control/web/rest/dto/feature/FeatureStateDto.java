package com.zarusz.control.web.rest.dto.feature;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.feature.*;
import com.zarusz.control.domain.feature.ir.IRFeature;
import com.zarusz.control.domain.feature.ir.IRSensorFeature;
import lombok.Data;

import java.util.Date;

/**
 * Created by Tomasz on 9/27/2015.
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(SwitchFeatureStateDto.class),
    @JsonSubTypes.Type(IRFeatureStateDto.class),
    @JsonSubTypes.Type(IRSensorFeatureStateDto.class),
    @JsonSubTypes.Type(TemperatureSensorFeatureStateDto.class),
    @JsonSubTypes.Type(HumiditySensorFeatureStateDto.class),
    @JsonSubTypes.Type(MotionSensorFeatureStateDto.class)
})
public abstract class FeatureStateDto {

    private Date updated;

    protected FeatureStateDto() {

    }

    protected FeatureStateDto(DeviceFeature feature) {
        updated = feature.getUpdated();
    }

    public static FeatureStateDto create(DeviceFeature feature) {
        if (feature instanceof SwitchFeature) {
            return new SwitchFeatureStateDto((SwitchFeature) feature);
        }
        if (feature instanceof IRFeature) {
            return new IRFeatureStateDto((IRFeature) feature);
        }
        if (feature instanceof IRSensorFeature) {
            return new IRSensorFeatureStateDto((IRSensorFeature) feature);
        }
        if (feature instanceof TemperatureSensorFeature) {
            return new TemperatureSensorFeatureStateDto((TemperatureSensorFeature) feature);
        }
        if (feature instanceof HumiditySensorFeature) {
            return new HumiditySensorFeatureStateDto((HumiditySensorFeature) feature);
        }
        if (feature instanceof MotionSensorFeature) {
            return new MotionSensorFeatureStateDto((MotionSensorFeature) feature);
        }
        throw new RuntimeException("Feature type not supported.");
    }

    public abstract void handle(DeviceFeature feature);
}
