package com.zarusz.control.domain.msg.events;

import com.zarusz.control.domain.feature.HumiditySensorFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HumidityChangedEvent extends TypedFeatureStateChangedEvent<HumiditySensorFeature> {

    private float oldValue;

    public HumidityChangedEvent() {
    }

    public HumidityChangedEvent(HumiditySensorFeature feature, float oldValue) {
        super(feature);
        this.oldValue = oldValue;
    }
}
