package com.zarusz.control.domain;

public class EventBus {

	public static <T> void publish(T event) {
		
	}
	
	public static <T> void subscribe(Class<T> clazz, Handler h) {
	}
	
	public static <T> void unsubscribe(Class<T> clazz, Handler h) {
	}

}
