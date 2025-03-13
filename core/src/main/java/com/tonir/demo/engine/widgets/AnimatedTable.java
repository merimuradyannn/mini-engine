package com.tonir.demo.engine.widgets;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Getter;
import lombok.Setter;

public abstract class AnimatedTable extends Table {
    protected float touchDownDuration = 0f;
    protected float touchUpDuration = 0f;
    protected boolean animating;
    protected boolean clicked;
    protected boolean shouldTouchUp;

    @Getter
    @Setter
    protected Runnable onClick;
    @Getter
    @Setter
    protected Runnable onTouchDown;

    protected boolean hasSound = true;

    public AnimatedTable () {
        addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (animating) return false;
                externalTouchDown();

                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                shouldTouchUp = true;
                if (hasSound) {
                    // TODO: 28.12.24 play sound
//                    API.get(AudioController.class).postGlobalEvent(WwiseCatalogue.EVENTS.BUTTON_TOUCH_UP);
                }
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void clicked (InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                clicked = true;
            }
        });
        setTouchable(Touchable.enabled);
    }

    public void externalTouchDown () {
        if (animating) return;
        animateTouchDown();
        if (hasSound) {
            // TODO: 28.12.24 play sound
//            API.get(AudioController.class).postGlobalEvent(WwiseCatalogue.EVENTS.BUTTON_TOUCH_DOWN);
        }
    }

    public void externalTouchUp () {
        shouldTouchUp = true;
        if (hasSound) {
            // TODO: 28.12.24 play sound
//            API.get(AudioController.class).postGlobalEvent(WwiseCatalogue.EVENTS.BUTTON_TOUCH_UP);
        }
    }

    @Override
    public void act (float delta) {
        super.act(delta);

        if (shouldTouchUp && !animating) {
            shouldTouchUp = false;
            animateTouchUp();
        }
    }

    protected void touchedDown () {
        animating = false;
        if (onTouchDown != null) {
            onTouchDown.run();
        }
    }

    protected void touchedUp () {
        animating = false;
        if (clicked) {
            clicked = false;
            if (onClick != null) {
                onClick.run();
            }
        }
    }

    protected void animateTouchDown () {
        animating = true;
        getAnimatedActor().addAction(Actions.sequence(
            getTouchDownAction(),
            Actions.run(this::touchedDown)
        ));
    }

    protected void animateTouchUp () {
        animating = true;
        getAnimatedActor().addAction(Actions.sequence(
            getTouchUpAction(),
            Actions.run(this::touchedUp)
        ));
    }

    protected abstract Action getTouchDownAction ();

    protected abstract Action getTouchUpAction ();

    protected abstract Actor getAnimatedActor ();
}
