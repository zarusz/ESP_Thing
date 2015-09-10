package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.topology.Device;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OnOffCommand extends TargetingDeviceCommand {

	private boolean on;

	public OnOffCommand(Device target, boolean on) {
		super(target);
		this.on = on;
	}
}
