package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.topology.DeviceFeature;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TargetingDeviceFeatureCommand<T extends DeviceFeature> extends TargetingDeviceCommand {

    @Setter(AccessLevel.PROTECTED)
    private T deviceFeature;

    public TargetingDeviceFeatureCommand() {
    }

    public TargetingDeviceFeatureCommand(T deviceFeature) {
        super(deviceFeature.getDevice());
        this.deviceFeature = deviceFeature;
    }
}


