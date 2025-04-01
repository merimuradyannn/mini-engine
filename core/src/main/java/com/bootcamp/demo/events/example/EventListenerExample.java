package com.bootcamp.demo.events.example;

import com.bootcamp.demo.events.core.EventHandler;
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.events.core.EventPriority;
import com.bootcamp.demo.managers.API;

public class EventListenerExample implements EventListener {

    public EventListenerExample () {
        API.get(EventModule.class).registerListener(this); // do not forget to register listener
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEventExample (EventExample event) {
        System.out.println(event.test);
    }
}
