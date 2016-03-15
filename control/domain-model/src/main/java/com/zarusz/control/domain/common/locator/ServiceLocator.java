package com.zarusz.control.domain.common.locator;

import java.util.Collection;

/**
 * Created by Tomasz on 10/3/2015.
 */
public interface ServiceLocator {

    <T> T resolve();
    <T> Collection<T> resolveAll();

}
