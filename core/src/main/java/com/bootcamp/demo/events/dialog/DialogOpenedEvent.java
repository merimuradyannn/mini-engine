package com.bootcamp.demo.events.dialog;

import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.dialogs.core.ADialog;

public class DialogOpenedEvent extends ADialogEvent {

    public static void fire (Class<? extends ADialog> dialogClass) {
        final DialogOpenedEvent event = API.get(EventModule.class).obtainEvent(DialogOpenedEvent.class);
        event.dialogClass = dialogClass;
        API.get(EventModule.class).fireEvent(event);
    }
}
