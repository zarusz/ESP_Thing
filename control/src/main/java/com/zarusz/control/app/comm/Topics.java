package com.zarusz.control.app.comm;

import com.zarusz.control.domain.device.Device;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Tomasz on 4/10/2016.
 */
public class Topics {

    public static final String DeviceDescription = "device/description";
    public static final String DeviceEvents = "device/events";
    private static final String DeviceDirect_Prefix = "device/";

    @Value("${control.mqtt.client_id}")
    @Getter
    private String me;

    public String getDeviceTopic(Device device) {
        return DeviceDirect_Prefix + device.getGuid();
    }

    public String getDeviceServiceTopic(Device device) {
        return DeviceDirect_Prefix + device.getGuid() + "/service";
    }

}
