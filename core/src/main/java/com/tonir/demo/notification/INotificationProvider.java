package com.tonir.demo.notification;

import com.badlogic.gdx.utils.Array;

public interface INotificationProvider {
    Priority getPriority ();
    Priority getMinPriority ();

    int getNotificationCount ();
    void setNotificationCount (int notificationCount);
    void updateNotificationCount ();
    void updateNotificationPriority ();
    void updateDependencyNotificationCount();

    boolean isShown (); // TODO: 05.02.24 remove later

    Array<Class<? extends INotificationProvider>> getDependencyProviders ();  // Providers you depend on
    Array<Class<? extends INotificationProvider>> getDependentProviders ();  // Providers depending on you

    enum Priority {
        ORANGE, RED, PURPLE
    }
}
