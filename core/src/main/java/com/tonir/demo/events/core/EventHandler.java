package com.tonir.demo.events.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    EventPriority priority () default EventPriority.NORMAL;

    Class<? extends EventFilter> filter () default DEFAULT.class;

    static final class DEFAULT implements EventFilter<Event> {
        @Override
        public boolean shouldExecute (Event event) {
            return true;
        }
    }
}
