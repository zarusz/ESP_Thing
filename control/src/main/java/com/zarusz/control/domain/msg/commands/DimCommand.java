package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.feature.DimFeature;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DimCommand extends TargetingDeviceFeatureCommand<DimFeature> {

	private float value;

	public DimCommand(DimFeature feature, float value) {
		super(feature);
		this.value = value;
	}
}
