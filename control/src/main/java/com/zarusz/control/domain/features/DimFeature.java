package com.zarusz.control.domain.features;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.zarusz.control.domain.EventBus;
import com.zarusz.control.domain.commands.DimCommand;
import com.zarusz.control.domain.topology.Device;

@Entity
@DiscriminatorValue("dim")
public class DimFeature extends DeviceFeature {

	private float value;
	
	protected DimFeature() {
	}
	
	public DimFeature(Device device) {
		super(device);
	}
	
	public void setValue(float value) {
		this.value = value;
		
		EventBus.publish(new DimCommand(getDevice(), value));
	}
	
	public float getValue() {
		return value;
	}
	
}
