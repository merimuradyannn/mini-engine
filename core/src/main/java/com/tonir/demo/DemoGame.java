package com.tonir.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tonir.demo.events.GameStartedEvent;
import com.tonir.demo.managers.API;
import com.tonir.demo.events.core.EventModule;
import com.tonir.demo.presenters.UI;

public class DemoGame extends Game {

    @Override
    public void create () {
        final UI ui = initUI();
        setScreen(ui);

        API.get(EventModule.class).fireEvent(GameStartedEvent.class);
    }

    private static UI initUI () {
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
        return new UI(viewport);
    }

    @Override
    public void dispose () {
        super.dispose();
        API.Instance().dispose();
        Gdx.app.exit();
    }
}
