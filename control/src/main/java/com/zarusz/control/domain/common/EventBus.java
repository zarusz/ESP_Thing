package com.zarusz.control.domain.common;

import net.engio.mbassy.bus.MBassador;

/**
 * Created by Tomasz on 4/5/2016.
 */
public class EventBus {

    private static MBassador instance;

    public static MBassador current() {
        return instance;
    }

    public static void setInstance(MBassador instance) {
        EventBus.instance = instance;
    }
}
