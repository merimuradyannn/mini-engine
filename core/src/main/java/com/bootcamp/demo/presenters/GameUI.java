package com.bootcamp.demo.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsList;
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.TestPage;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.pages.core.PageManager;
import lombok.Getter;
import lombok.Setter;

public class GameUI extends ScreenAdapter implements Disposable, EventListener {

    @Getter
    private final Stage stage;
    @Getter
    private final Table rootUI;
    @Getter
    private Cell<APage> mainPageCell;

    @Getter @Setter
    private boolean buttonPressed;

    // Constants for configuration
    private static final int DEFAULT_ROWS = 3;
    private static final int DEFAULT_COLS = 4;
    private static final Color GRID_BG_COLOR = Color.WHITE;
    private static final Color CELL_BG_COLOR = Color.GRAY;

    public GameUI(Viewport viewport) {
        API.Instance().register(GameUI.class, this);
        API.get(EventModule.class).registerListener(this);

        rootUI = new Table();
        rootUI.setFillParent(true);

        stage = new Stage(viewport);
        stage.addActor(rootUI);

       mainPageCell = rootUI.add().grow();
        //playground();
    }

    @Override
    public void render(float delta) {
        if (Gdx.app.getInput().isKeyJustPressed(Input.Keys.T)){
            API.get(PageManager.class).show(TestPage.class);
        }
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
