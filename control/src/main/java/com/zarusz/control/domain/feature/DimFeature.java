package com.zarusz.control.domain.feature;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.*;

@Setter
@Getter
@Entity
@DiscriminatorValue("dim")
public class DimFeature extends DeviceFeature {

    private float intensity;

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
