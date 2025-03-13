package com.tonir.demo.engine.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.tonir.demo.engine.Resources;
import com.tonir.demo.engine.Squircle;
import com.tonir.demo.notification.INotificationContainer;
import com.tonir.demo.notification.INotificationProvider;
import com.tonir.demo.notification.NotificationWidget;
import lombok.Getter;
import lombok.Setter;

public class OffsetButton extends Table implements INotificationContainer {

    @Getter
    protected BorderedTable frontTable;
    protected float offset;
    private ClickListener listener;
    protected Cell<BorderedTable> frontCell;
    private Table loadingSpineWrapper;
    protected Table backgroundTable = new Table();

    @Setter @Getter
    protected Runnable onClick;
    @Setter
    protected Runnable onTouchDown;
    @Setter
    protected float pressDuration = 0.05f;
    protected boolean pressing;
    protected boolean shouldRelease;
    protected boolean clicked;
    protected boolean releasing;
    protected boolean needsConfirmation = false;
    protected String confirmDialogHeaderText;
    protected String confirmDialogBodyText;
    protected float timer;

    private final Vector2 size = new Vector2();

    @Getter
    @Setter
    protected boolean enabled = true;
    @Getter
    protected boolean visuallyEnabled = true;
    @Getter
    protected Style style;

    public OffsetButton () {

    }

    public OffsetButton (Style style) {
        build(style);
    }

    public void build (Style style) {
        build();
        setStyle(style);
    }

    public void build () {
        frontTable = constructFrontTable();
        add(backgroundTable).grow();
        frontCell = backgroundTable.add(frontTable).grow();
        buildInner(frontTable);

        initListeners();
    }

    protected BorderedTable constructFrontTable () {
        final BorderedTable frontTable = new BorderedTable();
        frontTable.setTouchable(Touchable.disabled);
        return frontTable;
    }

    protected void buildInner(Table container) {

    }

    protected void initListeners () {
        listener = new ClickListener () {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0) return false;
                if (isAnimating()) return false;

                // get the size before animations
                size.set(getWidth(), getHeight());

                pressing = true;
                if (onTouchDown != null) onTouchDown.run();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (pressing) {
                    // schedule release
                    shouldRelease = true;
                } else releasing = true;
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                clicked = true;
            }
        };
        addListener(listener);
        setTouchable(Touchable.enabled);
    }

    protected boolean isAnimating () {
        return pressing || releasing;
    }

    protected boolean actLogicSkip = false;

    @Override
    public void act(float delta) {
        super.act(delta);

        if(actLogicSkip) return;

        if (!pressing && shouldRelease) {
            releasing = true; shouldRelease = false;
        }

        if (!isAnimating()) return;

        timer += delta;

        if (pressing) {
            final float padBottom = MathUtils.clamp(Interpolation.sineIn.apply(offset, 0, timer / pressDuration), 0, offset);
            final float padTop = offset - padBottom;
            padTop(padTop);
            frontCell.padBottom(padBottom);

            if (timer >= pressDuration) {
                frontCell.padBottom(0);
                pressing = false;
                timer = 0;
            }
        } else if (releasing) {
            final float padBottom = MathUtils.clamp(Interpolation.sineOut.apply(0, offset, timer / pressDuration), 0, offset);
            final float padTop = offset - padBottom;
            padTop(padTop);
            frontCell.padBottom(padBottom);

            if (timer >= pressDuration) {
                frontCell.padBottom(offset);
                releasing = false;
                timer = 0;

                if (clicked) {
                    clicked = false;
                    triggerClicked();
                }
            }
        }

        invalidate();
    }

    protected void triggerClicked () {
        if (onClick == null) return;

        if (needsConfirmation) {
//            ConfirmDialog.show(confirmDialogHeaderText, confirmDialogBodyText, new ConfirmDialog.ConfirmHandlerAdapter() {
//                @Override
//                public void confirm() {
//                    onClick.run();
//                }
//            });
        } else {
            onClick.run();
        }
    }

    public boolean isDisabled() {
        return !enabled;
    }

    public void enable () {
        this.enabled = true;
        setTouchable(Touchable.enabled);
        visuallyEnable();
    }

    public void disable () {
        this.enabled = false;
        setTouchable(Touchable.disabled);
        visuallyDisable();
    }

    public void visuallyEnable() {
        this.visuallyEnabled = true;
        // update background
        frontTable.setBorderDrawable(style.getBorderDrawable(true));
        frontTable.setBackground(style.getInnerBackground(true));
        setBackground(style.getOuterBackground(true));
        notificationWidgetWrapper.setVisible(true);
    }

    public void visuallyDisable() {
        this.visuallyEnabled = false;
        // update background
        frontTable.setBorderDrawable(style.getBorderDrawable(false));
        frontTable.setBackground(style.getInnerBackground(false));
        setBackground(style.getOuterBackground(false));
        notificationWidgetWrapper.setVisible(false);
    }

    public void setVisuallyEnabled (boolean enabled) {
        if (enabled) {
            visuallyEnable();
        } else {
            visuallyDisable();
        }
    }

    protected void updateVisually () {
        if (enabled) {
            visuallyEnable();
        }  else {
            visuallyDisable();
        }
    }

    public void setStyle (Style style) {
        this.style = style;

        final String backgroundPath = style.backgroundPath;
        if (backgroundPath == null) {
            this.frontTable.setBackground((Drawable) null);

            setBackground((Drawable) null);
        } else {
            setBackground(style.getBackgroundDrawable(style.enabledBorderColor));
            this.frontTable.setBackground(style.getBackgroundDrawable(style.enabledBackgroundColor));
        }

        final String borderPath = style.borderPath;
        if (borderPath == null) {
            this.frontTable.setBorderDrawable(null);
        } else {
            this.frontTable.setBorderDrawable(style.getBorderDrawable(style.enabledBorderColor));
        }

        this.offset = style.offset;
        this.frontCell.padBottom(offset);
    }

    public void setOffset (float offset) {
        this.offset = offset;
        this.frontCell.padBottom(offset);
        this.updateVisually();
    }

    public void addConfirmDialog (String headerText, String bodyText) {
        this.needsConfirmation = true;
        this.confirmDialogHeaderText = headerText;
        this.confirmDialogBodyText = bodyText;
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();

        if (clickBox != null) {
            updateClickBox();
        }
    }

    public enum Style {
        GREEN_35(Squircle.SQUIRCLE_35, Squircle.SQUIRCLE_35_BORDER, Color.valueOf("#7ed97b"), Color.valueOf("#519f4f"), 22),
        ;

        private final String backgroundPath;
        private final String borderPath;
        @Getter
        private final Color enabledBorderColor;
        @Getter
        private final Color enabledBackgroundColor;
        @Getter
        private final Color disabledBorderColor;
        @Getter
        private final Color disabledBackgroundColor;
        private final float offset;

        Style (Squircle backgroundPath, Squircle borderPath, Color enabledBackgroundColor, Color enabledBorderColor, float offset) {
            this(backgroundPath, borderPath, enabledBackgroundColor, enabledBorderColor, null, null, offset);
        }

        Style (String backgroundPath, String borderPath, Color enabledBackgroundColor, Color enabledBorderColor, float offset) {
            this(backgroundPath, borderPath, enabledBackgroundColor, enabledBorderColor, null, null, offset);
        }

        Style (Squircle backgroundPath, Squircle borderPath, Color enabledBackgroundColor, Color enabledBorderColor, Color disabledBackgroundColor, Color disabledBorderColor, float offset) {
            this(backgroundPath.getRegionName(), borderPath.getRegionName(), enabledBackgroundColor, enabledBorderColor, disabledBackgroundColor, disabledBorderColor, offset);
        }

        Style (String backgroundPath, String borderPath, Color enabledBackgroundColor, Color enabledBorderColor, Color disabledBackgroundColor, Color disabledBorderColor, float offset) {
            this.backgroundPath = backgroundPath;
            this.borderPath = borderPath;
            this.enabledBackgroundColor = enabledBackgroundColor;
            this.enabledBorderColor = enabledBorderColor;
            this.disabledBackgroundColor = disabledBackgroundColor == null ? Color.valueOf("#BABABA") : disabledBackgroundColor;
            this.disabledBorderColor = disabledBorderColor == null ? Color.valueOf("#7D7D7D") : disabledBorderColor;
            this.offset = offset;
        }

        public Drawable getInnerBackground (boolean enabled) {
            if (enabled) {
                return getBackgroundDrawable(enabledBackgroundColor);
            }
            return getBackgroundDrawable(disabledBackgroundColor);
        }

        public Drawable getOuterBackground (boolean enabled) {
            if (enabled) {
                return getBackgroundDrawable(enabledBorderColor) ;
            }
            return getBackgroundDrawable(disabledBorderColor);
        }

        public Drawable getBorderDrawable (boolean enabled) {
            if (enabled) {
                return getBorderDrawable(enabledBorderColor) ;
            }
            return getBorderDrawable(disabledBorderColor);
        }

        public Drawable getBackgroundDrawable (Color color) {
            if (backgroundPath == null) return null;
            return Resources.getDrawable(backgroundPath, color);
        }

        public Drawable getBorderDrawable (Color color) {
            if (borderPath == null) return null;
            return Resources.getDrawable(borderPath, color);
        }
    }


    // click box
    private Table clickBox;
    private float clickBoxPadX;
    private float clickBoxPadY;
    private float clickBoxOffsetX;
    private float clickBoxOffsetY;

    public void initClickBox () {
        if (clickBox != null) return;
        clickBox = new Table();
        addActor(clickBox);
        clickBox.setTouchable(Touchable.enabled);
    }

    public void setClickBoxPad (float pad) {
        setClickBoxPad(pad, pad);
    }

    public void setClickBoxPad (float padX, float padY) {
        if (clickBox == null) initClickBox();
        this.clickBoxPadX = padX;
        this.clickBoxPadY = padY;

        updateClickBox();
    }

    public void setClickBoxOffset (float offset) {
        setClickBoxOffset(offset, offset);
    }

    public void setClickBoxOffset (float offsetX, float offsetY) {
        if (clickBox == null) initClickBox();
        this.clickBoxOffsetX = offsetX;
        this.clickBoxOffsetY = offsetY;

        updateClickBox();
    }

    protected void updateClickBox () {
        final float width = clickBoxPadX + getWidth();
        final float height = clickBoxPadY + getHeight();
        clickBox.setSize(width, height);

        final float x = -clickBoxPadX / 2.0f + clickBoxOffsetX;
        final float y = -clickBoxPadY / 2.0f + clickBoxOffsetY;
        clickBox.setPosition(x, y);
    }

    @Override
    public void clearChildren() {
        super.clearChildren();
        if (clickBox != null) {
            addActor(clickBox);
        }
    }

    @Override
    public void setBackground (Drawable background) {
        backgroundTable.setBackground(background);
    }


    // notifications
    private final NotificationWidget.Wrapper notificationWidgetWrapper = new NotificationWidget.Wrapper();

    public void setNotificationAlignment (int alignment) {
        notificationWidgetWrapper.setNotificationAlignment(alignment);
    }

    public void setNotificationOffset (float offset) {
        setNotificationOffset(offset, offset);
    }

    public void setNotificationOffset (float offsetX, float offsetY) {
        setNotificationOffsetX(offsetX);
        setNotificationOffsetY(offsetY);
    }

    public void setNotificationOffsetX (float notificationOffsetX) {
        notificationWidgetWrapper.setNotificationOffsetX(notificationOffsetX);
    }

    public void setNotificationOffsetY (float notificationOffsetY) {
        notificationWidgetWrapper.setNotificationOffsetY(notificationOffsetY);
    }

    public NotificationWidget getNotificationWidget() {
        return notificationWidgetWrapper.getNotificationWidget();
    }

    @Override
    public void addNotificationWidget (NotificationWidget widget) {
        notificationWidgetWrapper.addNotificationWidget(widget);
        addActor(notificationWidgetWrapper);
    }

    public void addNotificationWidget () {
        addNotificationWidget(INotificationProvider.Priority.RED);
    }

    public void addNotificationWidget (INotificationProvider.Priority priority) {
        notificationWidgetWrapper.addNotificationWidget(priority);
    }

    public void setNotificationPriority (INotificationProvider.Priority priority) {
        if (getNotificationWidget() == null) {
            addNotificationWidget();
        }
        notificationWidgetWrapper.setPriority(priority);
    }

    public void showNotification() {
        if (getNotificationWidget() == null) {
            addNotificationWidget();
        }
        addActor(notificationWidgetWrapper);
    }

    public void hideNotification() {
        notificationWidgetWrapper.remove();
    }
}
