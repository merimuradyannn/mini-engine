package com.bootcamp.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.bootcamp.demo.events.GameStartedEvent;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;

public class DemoGame extends Game {

    @Override
    public void create () {
        setScreen(new GameScreen());
        API.get(EventModule.class).fireEvent(GameStartedEvent.class);
    }

    @Override
    public void dispose () {
        super.dispose();
        API.Instance().dispose();
        Gdx.app.exit();
    }
}
