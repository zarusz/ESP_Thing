package com.zarusz.control.app.comm;

import com.zarusz.control.device.messages.DeviceMessageProtos;

public class Mapper {
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
