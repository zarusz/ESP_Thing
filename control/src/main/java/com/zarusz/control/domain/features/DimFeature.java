package com.zarusz.control.domain.features;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.zarusz.control.domain.topology.Device;
import com.zarusz.control.domain.topology.DeviceFeature;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("dim")
public class DimFeature extends DeviceFeature {

    protected DimFeature() {
    }

    public DimFeature(Device device, Feature feature) {
        super(device, feature);
    }

//	public void setValue(float value) {
//		//EventBus.publish(new DimCommand(getDevice(), value));
//	}
//
//	public float getValue() {
//		return value;
//	}

}
