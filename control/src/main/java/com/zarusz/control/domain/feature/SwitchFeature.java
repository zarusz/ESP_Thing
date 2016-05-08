package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.common.EventBus;
import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.msg.commands.SwitchCommand;
import com.zarusz.control.domain.msg.events.SwitchChangedEvent;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("switch")
public class SwitchFeature extends DeviceFeature {

    @Getter
    @Column(name="`on`")
	private Boolean on;

	protected SwitchFeature() {
	}

	public SwitchFeature(Device device, Feature feature) {
        super(device, feature);
	}

	public void setOn(boolean on) {
        boolean oldValue = this.on;
        this.on = on;

        EventBus.current().publish(new SwitchCommand(this, on));

        if (oldValue != on) {
            EventBus.current().publish(new SwitchChangedEvent(this, oldValue));
        }
	}

	public boolean isOn() {
		return on;
	}

}
