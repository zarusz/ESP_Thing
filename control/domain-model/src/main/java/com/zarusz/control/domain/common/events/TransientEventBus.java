package com.zarusz.control.domain.common.events;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Tomasz on 10/3/2015.
 */
public class TransientEventBus implements EventBus {

    private final Map<String, Object> subscriptions = new HashMap<>();

    @Override
    public <TEventHandler> void publish(Event<TEventHandler> event) {
        Collection<TEventHandler> eventHandlers = getEventHandlers(event);
        eventHandlers.forEach(event::handle);
    }

    @Override
    public <TEventHandler> void subscribe(Event<TEventHandler> event, TEventHandler handler) {
        Collection<TEventHandler> eventHandlers = getEventHandlers(event);
        eventHandlers.add(handler);
    }

    @Override
    public <TEventHandler> void unsubscribe(Event<TEventHandler> event, TEventHandler handler) {
        Collection<TEventHandler> eventHandlers = getEventHandlers(event);
        eventHandlers.remove(handler);
    }

    protected  <TEventHandler> Collection<TEventHandler> getEventHandlers(Event<TEventHandler> event) {
        String eventId = event.getId();
        Collection<TEventHandler> eventHandlers = (Collection<TEventHandler>) subscriptions.get(eventId);
        if (eventHandlers == null) {
            eventHandlers = new HashSet<>();
            subscriptions.put(eventId, eventHandlers);
        }
        return eventHandlers;
    }

}
