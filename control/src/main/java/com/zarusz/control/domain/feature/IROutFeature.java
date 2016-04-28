package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.common.EventBus;
import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.msg.commands.IRCommand;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Tomasz on 4/23/2016.
 */
@Entity
@DiscriminatorValue("ir_out")
public class IROutFeature extends DeviceFeature {

    protected IROutFeature() {
    }

    public IROutFeature(Device device, Feature feature) {
        super(device, feature);
    }

    public void send(int value) {
        IRCommand cmd = new IRCommand(this, value);
        EventBus.current().publish(cmd);
    }

}
