package com.tonir.demo.events.dialog;

import com.tonir.demo.events.core.Event;
import com.tonir.demo.dialogs.core.ADialog;
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
