package com.zarusz.control.domain.common.events;

import java.util.function.Supplier;

/**
 * Created by Tomasz on 10/3/2015.
 */
public class Bus {

    private static Supplier<EventBus> provider;

    public static EventBus current() {
        return provider.get();
    }

    public static void setProvider(Supplier<EventBus> provider) {
        Bus.provider = provider;
    }

}
