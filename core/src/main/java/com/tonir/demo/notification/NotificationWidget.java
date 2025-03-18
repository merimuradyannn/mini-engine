package com.tonir.demo.notification;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.tonir.demo.engine.Labels;
import com.tonir.demo.engine.Resources;
import com.tonir.demo.localization.GameFont;
import lombok.Getter;
import lombok.Setter;

public class NotificationWidget extends Table {

    private final Image icon;
    private final Label numberLabel;
    private final Table numberTable;
    private final Table arrowTable;
    private float coolDown;
    private final int size;

    public NotificationWidget() {
        this(75);
    }
    public NotificationWidget(int size) {
        this(size, GameFont.BOLD_36);
    }

    public NotificationWidget(int size, GameFont font) {
        this.size = size;

        Stack stack = new Stack();
        numberTable = new Table();
        arrowTable = new Table();
        stack.add(numberTable);
        stack.add(arrowTable);

        icon = new Image();
        icon.setScaling(Scaling.fit);
        icon.setOrigin(size / 2.0f, size / 2.0f);
        arrowTable.add(icon).grow();

        numberTable.setBackground(Resources.getDrawable("ui/ui-notification-empty"));
        numberLabel = Labels.make(font, Color.WHITE);
        numberLabel.setAlignment(Align.center);
        numberTable.add(numberLabel).padBottom(5);

        setSize(size, size);
        addActor(stack);
        stack.setSize(size, size);
        stack.setPosition(getWidth()/2f - stack.getWidth()/2f, getHeight()/2f - stack.getHeight()/2f);

        numberTable.setVisible(false);
        arrowTable.setVisible(false);
        showNumber(false);
        setPriority(INotificationProvider.Priority.RED);
    }

    public void showAnim() {
        icon.setSize(size, size);
        icon.setOrigin(Align.center);
        icon.setScale(0.7f);
        icon.getColor().a = 0f;
        icon.addAction(Actions.sequence(
            Actions.scaleTo(1.3f, 1.3f, 0.1f),
            Actions.scaleTo(1f, 1f, 0.1f)
        ));
        icon.addAction(Actions.fadeIn(0.1f));
    }

    public void setPriority (INotificationProvider.Priority priority) {
        final Drawable iconDrawable;
        switch (priority) {
            case PURPLE: iconDrawable = Resources.getDrawable("ui/icons/ui-purple-notification-icon"); break;
            case ORANGE: iconDrawable = Resources.getDrawable("ui/icons/ui-orange-notification-icon"); break;
            default: iconDrawable = Resources.getDrawable("ui/ui-notification");
        }
        icon.setDrawable(iconDrawable);

        final Drawable bgDrawable;
        switch (priority) {
            case PURPLE: bgDrawable = Resources.getDrawable("ui/icons/ui-purple-notification-empty-icon"); break;
            case ORANGE: bgDrawable = Resources.getDrawable("ui/icons/ui-orange-notification-empty-icon"); break;
            default: bgDrawable = Resources.getDrawable("ui/ui-notification-empty");
        }
        numberTable.setBackground(bgDrawable);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        coolDown -= delta;

        if(coolDown <= 0) {
            coolDown = 4f + MathUtils.random(0f, 3f);
            rotateAnimation();
        }
    }

    public void rotateAnimation() {
        icon.setSize(size, size);
        icon.setOrigin(Align.center);
        icon.addAction(Actions.sequence(
            Actions.rotateBy(-20, 0.1f, Interpolation.fastSlow),
            Actions.rotateBy(60, 0.15f),
            Actions.rotateBy(-40, 0.15f)
        ));

        icon.addAction(Actions.sequence(
            Actions.scaleTo(1.15f, 1.15f, 0.1f),
            Actions.scaleTo(1f, 1f, 0.1f)
        ));

    }

    public void setCount(int count) {
        numberLabel.setText(count);
    }

    public void showNumber(boolean isShowNumber) {
        if(isShowNumber) {
            numberTable.setVisible(true);
            arrowTable.setVisible(false);
        } else {
            numberTable.setVisible(false);
            arrowTable.setVisible(true);
        }
    }

    public static class Wrapper extends Table {

        @Getter
        protected NotificationWidget notificationWidget;
        @Setter
        private int notificationAlignment = Align.topRight;
        @Setter
        private float notificationOffsetX = 30;
        @Setter
        private float notificationOffsetY = 30;

        public Wrapper () {
            setFillParent(true);
        }

        public void addNotificationWidget (NotificationWidget widget) {
            this.notificationWidget = widget;
            addActor(widget);
            updateNotificationWidgetPosition();
        }

        public void addNotificationWidget() {
            addNotificationWidget(INotificationProvider.Priority.RED);
        }

        public void addNotificationWidget (INotificationProvider.Priority priority) {
            final NotificationWidget widget = new NotificationWidget();
            widget.setPriority(priority);
            setNotificationAlignment(notificationAlignment);
            addNotificationWidget(widget);
        }

        private void updateNotificationWidgetPosition () {
            if (notificationWidget == null) return;

            final float posX;
            final float posY;

            if (Align.isTop(notificationAlignment)) {
                posY = getHeight() - notificationWidget.getHeight() / 2.0f - notificationOffsetY;
            } else {
                posY = - notificationWidget.getHeight() / 2.0f + notificationOffsetY;
            }

            if (Align.isLeft(notificationAlignment)) {
                posX = -notificationWidget.getWidth() / 2.0f + notificationOffsetX;
            } else {
                posX = getWidth() - notificationWidget.getWidth() / 2.0f - notificationOffsetX;
            }

            notificationWidget.setPosition(posX, posY);
        }

        public void setPriority (INotificationProvider.Priority priority) {
            notificationWidget.setPriority(priority);
        }

        @Override
        protected void sizeChanged () {
            super.sizeChanged();
            if (notificationWidget == null) return;
            updateNotificationWidgetPosition();
        }
    }
}
