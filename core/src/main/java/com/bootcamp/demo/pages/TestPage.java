package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.pages.core.APage;

public class TestPage extends APage {
    @Override
    protected void constructContent (Table content) {
        final Table testTable = new Table();
        testTable.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#bebebe")));
        final Table testTable2 = new Table();
        testTable2.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#bebebe")));
        final Table testTable3 = new Table();
        testTable3.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#bebebe")));
        final Table testTable4 = new Table();
        testTable4.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#bebebe")));
        final Table testTable5 = new Table();

        final Image gift = new Image(Resources.getDrawable("ui/ui-chat-gift-button-icon", Color.GREEN));
        gift.setScaling(Scaling.fit);

        final Table playground = new Table();
        playground.pad(30);
        playground.defaults().size(300);
        playground.add(testTable);
        playground.add(testTable2);
        playground.row();
        playground.add(testTable3);
        playground.add(gift).size(200, 600);

        final Table playground2 = new Table();
        playground2.setBackground(Resources.getDrawable("basics/white-squircle-35", Color.GREEN));
        final Image testImage = new Image();
        testImage.setDrawable(Resources.getDrawable("basics/white-squircle-35", Color.GREEN));
        playground2.add(testImage).size(300);

        content.add(playground2);
        content.debugAll();
    }
}
