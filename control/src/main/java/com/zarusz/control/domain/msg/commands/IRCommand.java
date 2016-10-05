package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.feature.IRFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
public class IRCommand extends TargetingDeviceFeatureCommand<IRFeature> {

    @Getter
	private int value;

	public IRCommand(IRFeature feature, int value) {
		super(feature);
		this.value = value;
	}
}
