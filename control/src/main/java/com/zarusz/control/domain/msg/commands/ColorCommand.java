package com.zarusz.control.domain.msg.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ColorCommand extends TargetingDeviceCommand {

	private byte red;
	private byte green;
	private byte blue;
	
}
