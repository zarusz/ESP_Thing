package com.zarusz.control.domain.msg;

import org.joda.time.DateTime;

import com.zarusz.control.domain.util.GuidUtil;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(of = { "id" })
public class Message {

	@Setter(AccessLevel.PROTECTED)
	private String id = GuidUtil.newGuid();

	@Setter(AccessLevel.PROTECTED)
	private DateTime created = new DateTime();

}
