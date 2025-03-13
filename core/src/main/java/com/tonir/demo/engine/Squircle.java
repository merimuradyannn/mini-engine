package com.tonir.demo.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import lombok.Getter;

public enum Squircle {
    SQUIRCLE_35(35, "white-squircle"),
    SQUIRCLE_35_BORDER(35, "white-squircle-border"),
    ;

    @Getter
    private final int radius;
    private final String name;

    Squircle (int radius, String name) {
        this.radius = radius;
        this.name = name;
    }

    public String getRegionName () {
        return "ui/" + name + "-" + radius;
    }

    public Drawable getDrawable () {
        return getDrawable(Color.WHITE);
    }

    public Drawable getDrawable (Color color) {
        return Resources.getDrawable("basics/" + name + "-" + radius, color);
    }

    public static Drawable getSquircle (int radius, Color color) {
        return Resources.getDrawable("basics/white-squircle-" + radius, color);
    }

    public static Drawable getSquircleBtm (int radius, Color color) {
        return Resources.getDrawable("basics/white-squircle-bottom-" + radius, color);
    }

    public static Drawable getBorder (int radius, Color color) {
        return Resources.getDrawable("basics/white-squircle-border-" + radius, color);
    }

    public static Drawable getBorderRight (int radius, Color color) {
        return Resources.getDrawable("basics/white-squircle-border-right" + radius, color);
    }

    public static Drawable getLeaf (int radius, Color color) {
        return Resources.getDrawable("basics/white-leaf-" + radius, color);
    }
}
