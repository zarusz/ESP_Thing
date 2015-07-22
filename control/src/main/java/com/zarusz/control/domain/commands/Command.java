package com.zarusz.control.domain.commands;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.zarusz.control.domain.topology.Device;
import com.zarusz.control.domain.util.GuidUtil;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Command {

	@Setter(AccessLevel.PROTECTED)
	private String guid = GuidUtil.newGuid();
	
	@Setter(AccessLevel.PROTECTED)
	private DateTime created = new DateTime();
	
	@Setter(AccessLevel.PROTECTED)
	private Set<Device> targets = new HashSet<>();
	
	protected Command() {
	}
	
	public Command(Device target) {
		addTarget(target);
	}

	public void addTarget(Device device) {
		targets.add(device);
	}
}
