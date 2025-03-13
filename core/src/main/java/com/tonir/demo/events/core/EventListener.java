package com.tonir.demo.events.core;

import com.tonir.demo.managers.API;

public interface EventListener {
    default void registerEventListener () {
        API.get(EventModule.class).registerListener(this);
    }
}
