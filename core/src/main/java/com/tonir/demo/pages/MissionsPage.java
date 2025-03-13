package com.tonir.demo.pages;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tonir.demo.pages.core.APage;

public class MissionsPage extends APage {
    @Override
    protected void constructContent(Table content) {
        final Table bottomSegment = constructBottomSegment();

        // assemble
        content.add(bottomSegment).height(600).growX().expandY().bottom();
    }

    private Table constructBottomSegment() {
        final Table playerGearSegment = constructPlayerGearSegment();
        final Table buttonSegment = constructButtonSegment();

        final Table segment = new Table();
        segment.defaults().growX().space(30);
        segment.add(playerGearSegment).growY();
        segment.row();
        segment.add(buttonSegment).height(200);
        return segment;
    }

    private Table constructPlayerGearSegment() {
        final Table segment = new Table();
        return segment;
    }

    private Table constructButtonSegment() {
        final Table segment = new Table();
        return segment;
    }
}
