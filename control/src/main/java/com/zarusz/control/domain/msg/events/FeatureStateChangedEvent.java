package com.zarusz.control.domain.msg.events;

import com.zarusz.control.domain.device.DeviceFeature;

/**
 * Created by Tomasz on 5/7/2016.
 */
public interface FeatureStateChangedEvent {

    DeviceFeature getFeature();
}
