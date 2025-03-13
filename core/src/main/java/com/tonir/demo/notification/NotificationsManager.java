package com.tonir.demo.notification;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.tonir.demo.managers.API;
import lombok.Getter;


public class NotificationsManager implements Disposable {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsManager.class);

    private final ObjectMap<Table, NotificationWidget> widgetMap = new ObjectMap<>();

    private final ObjectMap<INotificationProvider, Table> map = new ObjectMap<>();

    @Getter
    private final ObjectMap<Class<? extends INotificationProvider>, INotificationProvider> notificationProviderMap = new ObjectMap<>();

    private boolean initialLoadingComplete = false;

    private static void initNotificationProvider (Class<? extends INotificationProvider> clazz) {
        try {
            final INotificationProvider provider = ClassReflection.newInstance(clazz);
            NotificationsManager notificationsManager = API.get(NotificationsManager.class);
            notificationsManager.notificationProviderMap.put(clazz, provider);
        } catch (ReflectionException e) {
            logger.error("failed to initialize notification provider " + clazz.getSimpleName());
            throw new RuntimeException(e);
        }
    }

    public static <T extends INotificationProvider> T getNotification (Class<T> clazz) {
        NotificationsManager notificationsManager = API.get(NotificationsManager.class);
        if (!notificationsManager.notificationProviderMap.containsKey(clazz)) {
            initNotificationProvider(clazz);
        }
        return clazz.cast(notificationsManager.notificationProviderMap.get(clazz));
    }

    public static void unregisterButton (Class<? extends INotificationProvider> clazz, Table button) {
        NotificationsManager notificationsManager = API.get(NotificationsManager.class);
        if (notificationsManager.notificationProviderMap.containsKey(clazz)) {
            final INotificationProvider provider = notificationsManager.notificationProviderMap.get(clazz);
            NotificationsManager.unregisterButton(provider, button);
        }
    }

    public static INotificationProvider getProvider (Class<? extends INotificationProvider> clazz) {
        NotificationsManager notificationsManager = API.get(NotificationsManager.class);
        return notificationsManager.notificationProviderMap.get(clazz);
    }

    public static void addDependency (INotificationProvider dependentProvider, Class<? extends INotificationProvider> dependencyClazz) {
        if (dependentProvider.getClass().equals(dependencyClazz)) {
            logger.warn("trying to add dependency to the same notification provider " + dependencyClazz.getSimpleName());
            return;
        }
        // get or initialize the dependency provider instance
        final INotificationProvider dependencyProvider = getNotification(dependencyClazz);
        // add the dependency provider to the list of providers that the dependentProvider depends on
        dependentProvider.getDependencyProviders().add(dependencyClazz);
        // add the dependent provider to the list of providers that depend on the dependencyProvider
        dependencyProvider.getDependentProviders().add(dependentProvider.getClass());
    }

    public static void registerButton (Class<? extends INotificationProvider> clazz, Table button) {
        NotificationsManager.registerButton(getNotification(clazz), button);
    }

    public static void registerButton (INotificationProvider provider, Table button) {
        NotificationsManager notificationsManager = API.get(NotificationsManager.class);
        if (notificationsManager.map.containsKey(provider)) {
            logger.warn("trying to register a notification provider twice " + provider.getClass().getSimpleName());
            return;
        }
        notificationsManager.map.put(provider, button);
        final NotificationWidget widget = new NotificationWidget();
        notificationsManager.widgetMap.put(button, widget);
        widget.setVisible(false);

        if (API.get(NotificationsManager.class).initialLoadingComplete) {
            updateNotificationState(provider);
            updateButtonsState(provider);
        }
    }

    private static void updateDependencyProviders (INotificationProvider provider) {
        final Array<Class<? extends INotificationProvider>> dependencyProviders = provider.getDependencyProviders();
        for (Class<? extends INotificationProvider> dependencyProviderClass : dependencyProviders) {
            final INotificationProvider dependencyProvider = getNotification(dependencyProviderClass);
            updateDependencyProviders(dependencyProvider);
        }
        provider.updateDependencyNotificationCount();
        updateProvider(provider);
    }

    private static void updateDependentProviders (INotificationProvider provider) {
        updateDependentProviders(provider, false);
    }

    private static void updateDependentProviders (INotificationProvider provider, boolean forceUpdateDependencies) {
        final Array<Class<? extends INotificationProvider>> dependentProviders = provider.getDependentProviders();
        for (Class<? extends INotificationProvider> dependentProviderClass : dependentProviders) {
            final INotificationProvider dependentProvider = getNotification(dependentProviderClass);

            final int notificationCount = dependentProvider.getNotificationCount();
            final INotificationProvider.Priority priority = dependentProvider.getPriority();

            dependentProvider.updateDependencyNotificationCount();

            final int updatedNotificationCount = dependentProvider.getNotificationCount();
            final INotificationProvider.Priority updatedPriority = dependentProvider.getPriority();

            final boolean notificationCountChanged = notificationCount != updatedNotificationCount;
            final boolean priorityChanged = priority != updatedPriority;
            if (forceUpdateDependencies || notificationCountChanged || priorityChanged) {
                updateButtonsState(dependentProvider);
                updateDependentProviders(dependentProvider);
            }
        }
    }

    public static void unregisterButton (INotificationProvider provider, Table button) {
        NotificationsManager notificationsManager = API.get(NotificationsManager.class);
        notificationsManager.map.remove(provider);
        if (notificationsManager.widgetMap.containsKey(button)) {
            final NotificationWidget widget = notificationsManager.widgetMap.remove(button);
            widget.remove();
        }
    }

    public static void forceUpdateNotificationState (INotificationProvider provider) {
        updateProvider(provider);
        updateDependentProviders(provider, true);
        updateButtonsState(provider);
    }

    public static void updateNotificationState (INotificationProvider provider) {
        final boolean updated = updateProvider(provider);

        if (!updated) return;

        updateDependentProviders(provider);
        updateButtonsState(provider);
    }

    private static boolean updateProvider (INotificationProvider provider) {
        final int notificationCount = provider.getNotificationCount();
        final INotificationProvider.Priority priority = provider.getPriority();
        provider.updateNotificationCount();
        provider.updateNotificationPriority();
        final int updatedNotificationCount = provider.getNotificationCount();
        final INotificationProvider.Priority updatedPriority = provider.getPriority();

        // update notification status if notification count or priority changed after update
        return notificationCount != updatedNotificationCount || priority != updatedPriority;
    }

    public static void forceUpdateNotificationState (Class<? extends INotificationProvider> clazz) {
        NotificationsManager.forceUpdateNotificationState(getNotification(clazz));
    }

    public static void updateNotificationState (Class<? extends INotificationProvider> clazz) {
        NotificationsManager.updateNotificationState(getNotification(clazz));
    }

    public static void updateButtonsState (Class<? extends INotificationProvider> clazz) {
        NotificationsManager.updateButtonsState(getNotification(clazz));
    }

    public static void updateButtonsState (INotificationProvider provider) {
        NotificationsManager notificationsManager = API.get(NotificationsManager.class);
        final Table button = notificationsManager.map.get(provider);

        if (button == null) return;

        final int notificationCount = provider.getNotificationCount();

        // update state
        if (notificationCount > 0 && !provider.isShown()) {
            showNotificationOn(button, notificationCount, provider.getPriority());
        } else {
            hideNotificationOn(button);
        }
    }

    private static void showNotificationOn (Table button, int count, INotificationProvider.Priority priority) {
        if (button == null) {
            System.out.println("Ignore this case");
            return;
        }

        final NotificationsManager notificationsManager = API.get(NotificationsManager.class);
        final NotificationWidget widget = notificationsManager.widgetMap.get(button);
        if (widget == null) {
            System.out.println("DONT BREAK JUST IGNORE, but this is wrong");
            return;
        }

        // update priority
        widget.setPriority(priority);

        if (widget.getParent() == null) {
            if (button instanceof INotificationContainer) {
                INotificationContainer container = (INotificationContainer) button;
                if (!container.isShowNotification()) {
                    return;
                }
                widget.showNumber(container.isShowNumber());
                container.addNotificationWidget(widget);
            } else {
                button.pack();
                button.addActor(widget);
                widget.showNumber(false);
                widget.setPosition(-widget.getWidth() / 2f + 10, button.getHeight() - widget.getHeight() / 2f - 10);
            }
        }

        widget.setCount(count);
        widget.setVisible(true);
        widget.showAnim();
    }

    private static void hideNotificationOn (Table button) {
        if (button == null) {
            //This is bad, but fix for now
            return;
        }
        NotificationsManager notificationsManager = API.get(NotificationsManager.class);
        NotificationWidget widget = notificationsManager.widgetMap.get(button);
        if (widget == null) {
            return;//Do nothing, it wasn't in the map, who knows why, see https://github.com/rockbite/idle-outpost/commit/6b71ac5c303e7d0ec37ff384cdab481c23e23091
        }

        if (widget.getParent() != null) {
            widget.remove();
        }
    }

    public void updateAllProviders () {
        for (ObjectMap.Entry<Class<? extends INotificationProvider>, INotificationProvider> entry : notificationProviderMap) {
            INotificationProvider provider = entry.value;
            updateNotificationState(provider);
        }
    }

    public void updateAllButtonStates () {
        for (ObjectMap.Entry<INotificationProvider, Table> entry : map) {
            INotificationProvider provider = entry.key;
            updateButtonsState(provider);
        }

        API.get(NotificationsManager.class).initialLoadingComplete = true;
    }

    @Override
    public void dispose () {
        // EXTRA cautious
        if (map != null) {
            map.clear();
        }

        if (widgetMap != null) {
            widgetMap.clear();
        }

        if (notificationProviderMap != null) {
            notificationProviderMap.clear();
        }
    }

}
