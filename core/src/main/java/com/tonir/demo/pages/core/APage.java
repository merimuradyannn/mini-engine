package com.tonir.demo.pages.core;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Getter;

public abstract class APage extends Table {

    protected Table content;
    @Getter
    private boolean shown;

    public APage () {
        setTouchable(Touchable.enabled);
        addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void clicked (InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        content = new Table();
        add(content).grow();
        constructContent(content);
    }

    protected abstract void constructContent (Table content);

    public void show (Runnable onComplete) {
        shown = true;
        if (onComplete != null) {
            onComplete.run();
        }
    }

    public void hide (Runnable onComplete) {
        shown = false;
        if (onComplete != null) {
            onComplete.run();
        }
    }
}
