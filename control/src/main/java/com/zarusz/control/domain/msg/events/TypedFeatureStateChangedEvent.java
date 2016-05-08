package com.zarusz.control.domain.msg.events;

import com.zarusz.control.domain.msg.Event;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TypedFeatureStateChangedEvent<T extends DeviceFeature> extends Event implements FeatureStateChangedEvent {

    private T feature;

    public TypedFeatureStateChangedEvent() {
    }

    public TypedFeatureStateChangedEvent(T feature) {
        this.feature = feature;
    }

}
