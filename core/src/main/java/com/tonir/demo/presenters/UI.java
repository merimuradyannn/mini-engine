package com.tonir.demo.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tonir.demo.managers.API;
import com.tonir.demo.pages.core.APage;
import lombok.Getter;
import lombok.Setter;

public class UI implements Disposable, Screen {
    private final Stage stage;
    @Getter
    private final Table rootUI;
    @Getter
    private final Cell<APage> mainPageCell;

    @Getter @Setter
    private boolean buttonPressed;

    public UI (Viewport viewport) {
        API.Instance().register(UI.class, this);

        rootUI = new Table();
        rootUI.setFillParent(true);

        // init stage
        stage = new Stage(viewport);
        stage.addActor(rootUI);

        // init components
        final BottomPanel bottomPanel = new BottomPanel();

        // construct
        mainPageCell = rootUI.add().grow();
        rootUI.row();
        rootUI.add(bottomPanel).growX().height(300);
    }

    @Override
    public void show () {
        Gdx.input.setInputProcessor(stage);
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
    public void pause () {
        System.out.println("Game paused.");
        // save state if necessary
    }

    @Override
    public void resume () {
        System.out.println("Game resumed.");
        // restore state if necessary
    }

    @Override
    public void hide () {
        if (Gdx.input.getInputProcessor() == stage) {
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
