package com.zarusz.control.domain.features;

import javax.persistence.DiscriminatorValue;

import com.zarusz.control.domain.EventBus;
import com.zarusz.control.domain.commands.OnOffCommand;
import com.zarusz.control.domain.topology.Device;

@DiscriminatorValue("on_off")
public class OnOffFeature extends DeviceFeature {

	private boolean on;

	protected OnOffFeature() {
	}
	
	public OnOffFeature(Device device) {
		super(device);
	}
	
	public void setOn(boolean on) {
		this.on = on;
		
		OnOffCommand cmd = new OnOffCommand(getDevice(), on);
		EventBus.publish(cmd);
	}
	
	public boolean isOn() {
		return on;
	}
	
}
