package com.zarusz.control.domain.commands;

import com.zarusz.control.domain.topology.Device;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OnOffCommand extends Command {

	private boolean on;

	public OnOffCommand(Device target, boolean on) {
		super(target);
		this.on = on;
	}
}
