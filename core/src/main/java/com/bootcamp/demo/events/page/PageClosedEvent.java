package com.bootcamp.demo.events.page;

import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.pages.core.APage;

public class PageClosedEvent extends APageEvent {

    public static void fire (Class<? extends APage> pageClass) {
        final PageClosedEvent event = API.get(EventModule.class).obtainEvent(PageClosedEvent.class);
        event.pageClass = pageClass;
        API.get(EventModule.class).fireEvent(event);
    }
}

