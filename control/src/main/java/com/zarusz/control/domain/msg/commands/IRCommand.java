package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.feature.IRFeature;
import com.zarusz.control.domain.feature.ir.IRSignal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
public class IRCommand extends TargetingDeviceFeatureCommand<IRFeature> {

    @Getter
	private IRSignal signal;

	public IRCommand(IRFeature feature, IRSignal signal) {
		super(feature);
		this.signal = signal;
	}
}
