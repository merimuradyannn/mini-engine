package com.tonir.demo.events.dialog;

import com.tonir.demo.managers.API;
import com.tonir.demo.events.core.EventModule;
import com.tonir.demo.dialogs.core.ADialog;

public class DialogOpenedEvent extends ADialogEvent {

    public static void fire (Class<? extends ADialog> dialogClass) {
        final DialogOpenedEvent event = API.get(EventModule.class).obtainEvent(DialogOpenedEvent.class);
        event.dialogClass = dialogClass;
        API.get(EventModule.class).fireEvent(event);
    }
}
