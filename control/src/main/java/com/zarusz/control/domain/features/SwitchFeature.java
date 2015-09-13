package com.zarusz.control.domain.features;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.zarusz.control.domain.topology.Device;
import com.zarusz.control.domain.topology.DeviceFeature;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("switch")
public class SwitchFeature extends DeviceFeature {

	private boolean on;

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
