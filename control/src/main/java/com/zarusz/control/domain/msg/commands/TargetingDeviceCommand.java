package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.msg.Command;
import com.zarusz.control.domain.device.Device;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
public class TargetingDeviceCommand extends Command {

	@Setter(AccessLevel.PROTECTED)
	private Device device;

	public TargetingDeviceCommand() {
	}

	public TargetingDeviceCommand(Device device) {
        this.device = device;
	}
}
