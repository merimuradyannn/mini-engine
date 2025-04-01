package com.bootcamp.demo.events.page;

import com.bootcamp.demo.events.core.Event;
import com.bootcamp.demo.pages.core.APage;
import lombok.Getter;

@Getter
public class APageEvent extends Event {

    protected Class<? extends APage> pageClass;

    @Override
    public void reset () {
        super.reset();
        pageClass = null;
    }
}
