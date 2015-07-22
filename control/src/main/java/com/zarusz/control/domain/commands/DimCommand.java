package com.zarusz.control.domain.commands;

import com.zarusz.control.domain.topology.Device;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DimCommand extends Command {

	private float value;
	
	public DimCommand(Device target, float value) {
		super(target);
		this.value = value;
	}
}
