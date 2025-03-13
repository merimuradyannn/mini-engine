package com.tonir.demo.events.page;

import com.tonir.demo.events.core.Event;
import com.tonir.demo.pages.core.APage;
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
