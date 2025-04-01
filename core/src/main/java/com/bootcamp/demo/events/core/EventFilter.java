package com.bootcamp.demo.events.core;

public interface EventFilter<T extends Event> {

	boolean shouldExecute (T event);

}
