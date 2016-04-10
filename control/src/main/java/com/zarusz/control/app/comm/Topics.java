package com.zarusz.control.app.comm;

import com.zarusz.control.domain.device.HubDevice;

/**
 * Created by Tomasz on 4/10/2016.
 */
public final class Topics {

    public static final String DeviceDescription = "device/description";
    public static final String DeviceEvents = "device/events";
    private static final String DeviceDirect_Prefix = "device/";

    public static String getDeviceTopic(HubDevice hub) {
        return DeviceDirect_Prefix + hub.getGuid();
    }

}
