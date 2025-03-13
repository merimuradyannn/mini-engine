package com.tonir.demo.engine.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.tonir.demo.engine.Squircle;
import lombok.Getter;

public class BorderedTable extends PressableTable {
    protected Drawable emptyBorder = Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("81776e"));
    protected Drawable emptyBackground = Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("c2b8b0"));

    @Getter
    protected final Image borderImage;
    protected int borderSize = 8;

    public BorderedTable () {
        this(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("c2b8b0")), Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("81776e")));
    }

    public BorderedTable (Drawable backgroundDrawable, Drawable borderDrawable) {
        borderImage = new Image(borderDrawable);
        borderImage.setFillParent(true);
        borderImage.setTouchable(Touchable.disabled);
        addActor(borderImage);

        setBackground(backgroundDrawable);
        pad(borderSize); // pad for border
    }

    public void set (Drawable backgroundDrawable, Drawable borderDrawable) {
        setBackground(backgroundDrawable);
        setBorderDrawable(borderDrawable);
    }

    public void setBorderDrawable (Drawable borderDrawable) {
        borderImage.setDrawable(borderDrawable);
    }

    public void setEmpty () {
        setBackground(emptyBackground);
        borderImage.setDrawable(emptyBorder);
    }

    public void bringBorderFront () {
        getBorderImage().toFront();
    }
}
