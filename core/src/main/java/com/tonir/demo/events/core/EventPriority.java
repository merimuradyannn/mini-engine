package com.tonir.demo.events.core;

import lombok.Getter;

@Getter
public enum EventPriority {
    LOWEST(0),
    LOW(1),
    NORMAL(2),
    HIGH(3),
    HIGHEST(4),
    MONITOR(5);

    private final int priority;

    EventPriority (int priority) {
        this.priority = priority;
    }
}
