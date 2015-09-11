package com.zarusz.control.domain.msg.events;

import com.zarusz.control.domain.msg.Event;
import com.zarusz.control.domain.topology.DeviceFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SensorChangedEvent<T extends DeviceFeature> extends Event {

    private T feature;

    public SensorChangedEvent() {
    }

    public SensorChangedEvent(T feature) {
        this.feature = feature;
    }
}
