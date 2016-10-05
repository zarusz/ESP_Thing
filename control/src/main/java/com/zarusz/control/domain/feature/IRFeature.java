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
@DiscriminatorValue("IR")
public class IRFeature extends DeviceFeature {

    protected IRFeature() {
    }

    public IRFeature(Device device, Feature feature, int port) {
        super(device, feature, port);
    }

    public void send(int value) {
        IRCommand cmd = new IRCommand(this, value);
        EventBus.current().publish(cmd);
    }

}
