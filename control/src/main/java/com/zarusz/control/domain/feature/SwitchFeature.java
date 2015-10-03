package com.zarusz.control.domain.feature;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("switch")
public class SwitchFeature extends DeviceFeature {

	private Boolean on;

	protected SwitchFeature() {
	}

	public SwitchFeature(Device device, Feature feature) {
		super(device, feature);
	}

	public void setOn(boolean on) {
		this.on = on;

		//SwitchCommand cmd = new SwitchCommand(getDevice(), on);
		//EventBus.publish(cmd);
	}

	public boolean isOn() {
		return on;
	}

}
