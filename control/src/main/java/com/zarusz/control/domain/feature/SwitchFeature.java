package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.common.EventBus;
import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.msg.commands.SwitchCommand;
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
		this.on = on;

		SwitchCommand cmd = new SwitchCommand(this, on);
        EventBus.current().publish(cmd);
	}

	public boolean isOn() {
		return on;
	}

}
