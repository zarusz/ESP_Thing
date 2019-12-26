package com.zarusz.control.domain.common.locator;

import java.util.function.Supplier;

/**
 * Created by Tomasz on 10/3/2015.
 */
public class Locator {

    private static Supplier<ServiceLocator> provider;

    public static void setProvider(Supplier<ServiceLocator> provider) {
        Locator.provider = provider;
    }

    public static ServiceLocator current() {
        return provider.get();
    }

}
