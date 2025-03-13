package com.tonir.demo.events.dialog;

import com.tonir.demo.managers.API;
import com.tonir.demo.events.core.EventModule;
import com.tonir.demo.dialogs.core.ADialog;

public class DialogClosedEvent extends ADialogEvent {

    public static void fire (Class<? extends ADialog> dialogClass) {
        final DialogClosedEvent event = API.get(EventModule.class).obtainEvent(DialogClosedEvent.class);
        event.dialogClass = dialogClass;
        API.get(EventModule.class).fireEvent(event);
    }
}
