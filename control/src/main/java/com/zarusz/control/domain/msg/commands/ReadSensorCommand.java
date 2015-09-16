package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.device.DeviceFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReadSensorCommand extends TargetingDeviceFeatureCommand {

    public ReadSensorCommand() {
    }

    public ReadSensorCommand(DeviceFeature feature) {
        super(feature);
    }
}
