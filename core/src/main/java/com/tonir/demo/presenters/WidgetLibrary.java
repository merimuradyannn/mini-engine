package com.tonir.demo.presenters;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tonir.demo.engine.widgets.CustomScrollPane;

public class WidgetLibrary {

    private WidgetLibrary() {}

    public static CustomScrollPane verticalScrollPane (Table content) {
        final CustomScrollPane scrollPane = new CustomScrollPane(content);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setElasticOverscroll(true);
        return scrollPane;
    }

    public static CustomScrollPane horizontalScrollPane (Table content) {
        final CustomScrollPane scrollPane = new CustomScrollPane(content);
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setElasticOverscroll(true);
        return scrollPane;
    }
}
