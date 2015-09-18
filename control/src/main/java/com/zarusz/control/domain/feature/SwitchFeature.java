package com.zarusz.control.domain.feature;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true, of = {})
@ToString(of = {"id", "port"})
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
