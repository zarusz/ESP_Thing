package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.feature.IROutFeature;
import com.zarusz.control.domain.feature.SwitchFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
public class IRCommand extends TargetingDeviceFeatureCommand<IROutFeature> {

    @Getter
	private int value;

	public IRCommand(IROutFeature feature, int value) {
		super(feature);
		this.value = value;
	}
}
