package com.tonir.demo.events.example;

import com.tonir.demo.events.core.EventHandler;
import com.tonir.demo.events.core.EventListener;
import com.tonir.demo.events.core.EventPriority;

public class EventListenerExample implements EventListener {

    public EventListenerExample () {
        registerEventListener(); // do not forget to register listener
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEventExample (EventExample event) {
        System.out.println(event.test);
    }
}
