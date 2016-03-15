package com.zarusz.control.domain.common.events;

public interface EventBus {

	<TEventHandler> void publish(Event<TEventHandler> event);
	<TEventHandler> void subscribe(Event<TEventHandler> event, TEventHandler handler);
	<TEventHandler> void unsubscribe(Event<TEventHandler> event, TEventHandler handler);

}
