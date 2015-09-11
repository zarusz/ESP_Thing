package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.features.SwitchFeature;
import com.zarusz.control.domain.topology.Device;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SwitchCommand extends TargetingDeviceFeatureCommand<SwitchFeature> {

	private boolean on;

	public SwitchCommand(SwitchFeature feature, boolean on) {
		super(feature);
		this.on = on;
	}
}
