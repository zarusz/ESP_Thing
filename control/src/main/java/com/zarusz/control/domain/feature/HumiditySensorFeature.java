package com.zarusz.control.domain.feature;

import com.zarusz.control.domain.common.EventBus;
import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.msg.events.HumidityChangedEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Tomasz on 4/18/2016.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("sensor_humidity")
public class HumiditySensorFeature extends DeviceFeature {

    private float humidity;

    protected HumiditySensorFeature() {
    }

    public HumiditySensorFeature(Device device, Feature feature) {
        super(device, feature);
    }

    public void updateValue(float value) {
        float oldValue = humidity;

        setHumidity(value);
        // TODO store history record

        if (oldValue != humidity) {
            EventBus.current().publish(new HumidityChangedEvent(this, oldValue));
        }
    }
}
