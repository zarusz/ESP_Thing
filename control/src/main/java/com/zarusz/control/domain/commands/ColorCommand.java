package com.zarusz.control.domain.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ColorCommand extends Command {

	private byte red;
	private byte green;
	private byte blue;
	
}
