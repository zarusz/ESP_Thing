package com.zarusz.control.app.comm;

import java.io.Closeable;
import java.io.IOException;

import com.zarusz.control.domain.msg.Event;
import com.zarusz.control.domain.msg.Message;
import com.zarusz.control.domain.msg.commands.TargetingDeviceCommand;

import lombok.Data;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;

@Data
public class RFHub implements Closeable {

	private final MBassador<Message> bus;

	public RFHub(MBassador<Message> bus) {
		super();
		this.bus = bus;

		bus.subscribe(this);
		// TODO start RF conn
	}

	@Override
	public void close() throws IOException {
		// TODO close RF conn
		bus.unsubscribe(this);
	}

	@Handler
	public void handleDeviceCommand(TargetingDeviceCommand cmd) {

		// TODO: send to device

	}

	protected void displatchEvent(Event e) {
		bus.publishAsync(e);
	}

}
