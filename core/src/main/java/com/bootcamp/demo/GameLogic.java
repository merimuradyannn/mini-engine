package com.bootcamp.demo;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bootcamp.demo.managers.API;
import lombok.Getter;

public class GameLogic extends ScreenAdapter implements Disposable {

    @Getter
    private final Stage stage;

    public GameLogic (Viewport viewport) {
        API.Instance().register(GameLogic.class, this);

        stage = new Stage(viewport);
    }

    @Override
    public void render (float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
