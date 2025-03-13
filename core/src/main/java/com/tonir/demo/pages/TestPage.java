package com.tonir.demo.pages;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tonir.demo.pages.core.APage;

public class TestPage extends APage {
    @Override
    protected void constructContent (Table content) {
        debugAll();
    }
}
