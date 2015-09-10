package com.zarusz.control.domain.msg.commands;

import java.util.HashSet;
import java.util.Set;

import com.zarusz.control.domain.topology.Device;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
public class TargetingDeviceCommand extends Command {

	@Setter(AccessLevel.PROTECTED)
	private Set<Device> targets = new HashSet<>();
	
	public TargetingDeviceCommand() {
	}
	
	public TargetingDeviceCommand(Device target) {
		addTarget(target);
	}

	public void addTarget(Device device) {
		targets.add(device);
	}

}
