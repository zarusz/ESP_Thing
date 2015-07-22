package com.zarusz.control.domain.util;

import java.util.UUID;

public class GuidUtil {

	public static String newGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
}
