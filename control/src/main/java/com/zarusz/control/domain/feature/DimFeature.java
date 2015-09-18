package com.zarusz.control.domain.feature;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true, of = {})
@ToString(of = {"id", "port"})
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
