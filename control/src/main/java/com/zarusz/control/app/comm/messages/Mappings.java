package com.zarusz.control.app.comm.messages;

import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.feature.FeatureType;

/**
 * Created by Tomasz on 10/1/2016.
 */
public class Mappings {

    public static FeatureType mapFeatureType(DeviceMessageProtos.FeatureType msgFeatureType) {
        switch (msgFeatureType) {
            case SWITCH:
                return FeatureType.Switch;
            case IR:
                return FeatureType.IR;
            case SENSOR_IR:
                return FeatureType.SensorIR;
            case SENSOR_HUMIDITY:
                return FeatureType.SensorHumidity;
            case SENSOR_TEMPERATURE:
                return FeatureType.SensorTemperature;
            case SENSOR_MOTION:
                return FeatureType.SensorMotion;
        }
        throw new RuntimeException("The feature type is not supported");
    }

    public static DeviceMessageProtos.IRFormat map(com.zarusz.control.domain.feature.ir.IRFormat format) {
        switch (format) {
            case NEC:
                return DeviceMessageProtos.IRFormat.NEC;
            case SONY:
                return DeviceMessageProtos.IRFormat.SONY;
        }
        throw new RuntimeException("Could not map value");
    }
}
