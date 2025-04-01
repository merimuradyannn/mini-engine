package com.bootcamp.demo.dialogs.core;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.bootcamp.demo.events.dialog.DialogClosedEvent;
import com.bootcamp.demo.events.dialog.DialogOpenedEvent;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.presenters.GameUI;

public class DialogManager implements Disposable {

    private final ObjectMap<Class<? extends ADialog>, ADialog> cache = new ObjectMap<>();

    private final Array<ADialog> openedDialogs = new Array<>();

    public <T extends ADialog> T getDialog (Class<T> clazz) {
        if (!cache.containsKey(clazz)) {
            try {
                final ADialog dialog = ClassReflection.newInstance(clazz);
                cache.put(clazz, dialog);
                return (T) dialog;
            } catch (ReflectionException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) cache.get(clazz);
    }

    public void show (Class<? extends ADialog> clazz) {
        final ADialog dialog = getDialog(clazz);
        if (dialog == null) return;

        API.get(GameUI.class).getRootUI().addActor(dialog);
        // TODO: 30.12.24 left null for now
        dialog.show(null);
        openedDialogs.add(dialog);

        DialogOpenedEvent.fire(clazz);
    }

    public void hide (Class<? extends ADialog> clazz) {
        final ADialog dialog = getDialog(clazz);
        if (dialog == null) return;

        dialog.hide(() -> {
            openedDialogs.removeValue(dialog, true);
            dialog.remove();
        });

        DialogClosedEvent.fire(clazz);
    }

    @Override
    public void dispose () {
        cache.clear();
    }
}
