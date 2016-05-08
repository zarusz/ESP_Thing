package com.zarusz.control.domain.msg.events;

import com.zarusz.control.domain.feature.SwitchFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SwitchChangedEvent extends TypedFeatureStateChangedEvent<SwitchFeature> {

    private boolean oldValue;

    public SwitchChangedEvent() {
    }

    public SwitchChangedEvent(SwitchFeature feature, boolean oldValue) {
        super(feature);
        this.oldValue = oldValue;
    }
}
