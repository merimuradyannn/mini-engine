package com.tonir.demo.managers;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.tonir.demo.localization.Localization;
import com.tonir.demo.events.core.EventModule;
import com.tonir.demo.engine.Resources;
import com.tonir.demo.pages.core.PageManager;

public class API implements Disposable {

    private static API api;

    private final ObjectMap<Class<?>, Disposable> apiMap = new ObjectMap<>();

    public static API Instance () {
        if (api == null) {
            api = new API();
        }
        return api;
    }

    public API () {
        initMinimal();
    }

    public static <U> U get (Class<U> clazz) {
        return clazz.cast(Instance().apiMap.get(clazz));
    }

    public void initMinimal () {
        register(EventModule.class);
        register(Resources.class);
        register(Localization.class);
        register(PageManager.class);
    }

    public <T extends Disposable> void register (Class<T> key, T object) {
        if (apiMap.containsKey(key)) return;
        apiMap.put(key, object);
    }

    public <T extends Disposable> void register (Class<T> clazz) {
        if (apiMap.containsKey(clazz)) return;
        try {
            T instance = ClassReflection.newInstance(clazz);
            apiMap.put(clazz, instance);
        } catch (ReflectionException e) {
            throw new RuntimeException("Failed to instantiate class: " + clazz.getName(), e);
        }
    }

    public <T extends Disposable> void register (T object) {
        register((Class<T>) object.getClass(), object);
    }

    @Override
    public void dispose () {
        for (Disposable disposable : apiMap.values()) {
            disposable.dispose();
        }
        apiMap.clear();
    }
}
