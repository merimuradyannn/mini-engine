package com.bootcamp.demo.events.dialog;

import com.bootcamp.demo.events.core.Event;
import com.bootcamp.demo.dialogs.core.ADialog;
import lombok.Getter;

@Getter
public abstract class ADialogEvent extends Event {

    protected Class<? extends ADialog> dialogClass;

    @Override
    public void reset () {
        super.reset();
        dialogClass = null;
    }
}
