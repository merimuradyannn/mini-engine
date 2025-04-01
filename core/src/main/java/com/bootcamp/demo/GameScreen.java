package com.bootcamp.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.bootcamp.demo.presenters.GameUI;

public final class GameScreen implements Screen {

    private final GameUI ui;
    private final GameLogic game;

    public GameScreen () {
        final OrthographicCamera camera = new OrthographicCamera();
        final float aspect = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        final float decisionAspect = 1440.0f / 2560.0f;

        final float width, height;
        if (aspect < decisionAspect) {
            // aspect ratio lower than mid-aspect, usually better to fix width
            width = 1440;
            height = aspect * width;
        } else {
            // aspect ratio lower than mid-aspect, usually better to fix height
            height = 2560;
            width = height * aspect;
        }
        // detect which viewport is better for current aspect
        final ExtendViewport viewport = new ExtendViewport(width, height, camera);

        // init game and ui
        ui = new GameUI(viewport);
        game = new GameLogic(viewport);
    }

    @Override
    public void show () {
        // if needed, set an input multiplexer to handle both stages
        Gdx.input.setInputProcessor(ui.getStage());
    }

    @Override
    public void render (final float delta) {
        // clear screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        game.render(delta);
        ui.render(delta);
    }

    @Override
    public void resize (final int width, final int height) {
        game.resize(width, height);
        ui.resize(width, height);
    }

    @Override
    public void pause () {
        System.out.println("Game paused.");
        game.pause();
        ui.pause();
    }

    @Override
    public void resume () {
        System.out.println("Game resumed.");
        game.resume();
        ui.resume();
    }

    @Override
    public void hide () {
        Gdx.input.setInputProcessor(null);

        game.hide();
        ui.hide();
    }

    @Override
    public void dispose () {
        game.dispose();
        ui.dispose();
    }
}
