package com.zarusz.control.domain.msg.events;

import com.zarusz.control.domain.features.MotionSensorFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MovementDetectedEvent extends SensorChangedEvent<MotionSensorFeature> {

    public MovementDetectedEvent() {
    }

    public MovementDetectedEvent(MotionSensorFeature feature) {
        super(feature);
    }

}
