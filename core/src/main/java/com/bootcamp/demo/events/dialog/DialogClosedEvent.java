package com.bootcamp.demo.events.dialog;

import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.dialogs.core.ADialog;

public class DialogClosedEvent extends ADialogEvent {

    public static void fire (Class<? extends ADialog> dialogClass) {
        final DialogClosedEvent event = API.get(EventModule.class).obtainEvent(DialogClosedEvent.class);
        event.dialogClass = dialogClass;
        API.get(EventModule.class).fireEvent(event);
    }
}
