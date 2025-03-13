package com.tonir.demo.events.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.reflect.Annotation;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

public class EventModule implements Disposable {

    private final ObjectMap<Class<? extends Event>, Array<RegisteredListener<?>>> listeners = new ObjectMap<>();

    private final Comparator<RegisteredListener<?>> priorityComparator = (o1, o2) ->
        Integer.compare(o2.getPriority().getPriority(), o1.getPriority().getPriority());

    public <T extends Event> T obtainEvent (final Class<T> eventType) {
        return Pools.obtain(eventType);
    }

    public <T extends Event> void freeEvent (final T event) {
        Pools.free(event);
    }

    public <T extends Event> void fireEvent (final T event) {
        if (!listeners.containsKey(event.getClass())) {
            registerEvent(event.getClass());
        }

        final Array<RegisteredListener<?>> registeredListeners = listeners.get(event.getClass());
        if (registeredListeners == null) {
            return;
        }

        for (final RegisteredListener<?> listener : registeredListeners) {
            @SuppressWarnings("unchecked") final RegisteredListener<T> castedListener = (RegisteredListener<T>) listener;

            if (!castedListener.getFilter().shouldExecute(event)) {
                continue;
            }

            try {
                castedListener.getMethod().invoke(castedListener.getListener(), event);
            } catch (final ReflectionException e) {
                Gdx.app.error("EventModule", "Failed to invoke event handler", e);
            }
        }
    }

    public <T extends Event> T fireEvent (final Class<T> eventType) {
        final T event = obtainEvent(eventType);
        fireEvent(event);
        return event;
    }

    private <T extends Event> void registerEvent (final Class<T> eventType) {
        final Method[] methods = ClassReflection.getMethods(eventType);
        for (final Method method : methods) {
            final Annotation handlerAnnotation = method.getDeclaredAnnotation(EventHandler.class);
            if (handlerAnnotation == null) {
                continue;
            }

            if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                throw new IllegalArgumentException(
                    "Method " + method.getName() + " in " + eventType.getName() +
                        " must have exactly one parameter of type Event."
                );
            }

            @SuppressWarnings("unchecked") final Class<? extends Event> eventParamType = (Class<? extends Event>) method.getParameterTypes()[0];
            final EventPriority priority = handlerAnnotation.getAnnotation(EventHandler.class).priority();
            final EventFilter<?> filter = getFilterInstance(handlerAnnotation.getAnnotation(EventHandler.class).filter());

            Array<RegisteredListener<?>> registeredListeners = listeners.get(eventParamType);
            if (registeredListeners == null) {
                registeredListeners = new Array<>();
                listeners.put(eventParamType, registeredListeners);
            }

            // Register event as its own listener if necessary
            registeredListeners.add(new RegisteredListener<>((EventListener) obtainEvent(eventType), method, priority, filter));
        }

        for (final Array<RegisteredListener<?>> registeredListeners : listeners.values()) {
            registeredListeners.sort(priorityComparator);
        }
    }

    public void registerListener (EventListener listener) {
        final Method[] methods = ClassReflection.getMethods(listener.getClass());
        for (final Method method : methods) {
            final Annotation handlerAnnotation = method.getDeclaredAnnotation(EventHandler.class);
            if (handlerAnnotation == null) {
                continue;
            }

            if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                throw new IllegalArgumentException(
                    "Method " + method.getName() + " in " + listener.getClass().getName() +
                        " must have exactly one parameter of type Event."
                );
            }

            @SuppressWarnings("unchecked")
            final Class<? extends Event> eventType = (Class<? extends Event>) method.getParameterTypes()[0];
            final EventPriority priority = handlerAnnotation.getAnnotation(EventHandler.class).priority();
            final EventFilter<?> filter = getFilterInstance(handlerAnnotation.getAnnotation(EventHandler.class).filter());

            Array<RegisteredListener<?>> registeredListeners = listeners.get(eventType);
            if (registeredListeners == null) {
                registeredListeners = new Array<>();
                listeners.put(eventType, registeredListeners);
            }

            registeredListeners.add(new RegisteredListener<>(listener, method, priority, filter));
        }

        for (final Array<RegisteredListener<?>> registeredListeners : listeners.values()) {
            registeredListeners.sort(priorityComparator);
        }
    }

    private EventFilter<?> getFilterInstance (final Class<? extends EventFilter> filterClass) {
        try {
            if (filterClass == EventHandler.DEFAULT.class) {
                return new EventHandler.DEFAULT();
            }
            return ClassReflection.newInstance(filterClass);
        } catch (final ReflectionException e) {
            throw new RuntimeException("Failed to create filter instance for " + filterClass.getName(), e);
        }
    }

    @Override
    public void dispose () {

    }

    public void registerListener () {

    }

    @Getter
    @RequiredArgsConstructor
    private static class RegisteredListener<T extends Event> {
        private final EventListener listener;
        private final Method method;
        private final EventPriority priority;
        private final EventFilter<T> filter;
    }
}
