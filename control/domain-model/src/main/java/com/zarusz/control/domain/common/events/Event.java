package com.zarusz.control.domain.common.events;

/**
 * Created by Tomasz on 10/3/2015.
 */
public interface Event<TEventHandler> {
    String getId();
    void handle(TEventHandler handler);
}
