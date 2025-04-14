package com.bootcamp.demo.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.bootcamp.demo.managers.API;
import lombok.Getter;

public enum Squircle {
    SQUIRCLE_8(8, "ui-white-squircle"),
    SQUIRCLE_10(10, "ui-white-squircle"),
    SQUIRCLE_12(12, "ui-white-squircle"),
    SQUIRCLE_15(15, "ui-white-squircle"),
    SQUIRCLE_20(20, "ui-white-squircle"),
    SQUIRCLE_16(16, "ui-white-squircle"),
    SQUIRCLE_25(25, "ui-white-squircle"),
    SQUIRCLE_30(30, "ui-white-squircle"),
    SQUIRCLE_35(35, "ui-white-squircle"),
    SQUIRCLE_40(40, "ui-white-squircle"),
    SQUIRCLE_50(50, "ui-white-squircle"),
    SQUIRCLE_90(90, "ui-white-squircle"),

    SQUIRCLE_35_CORNERS(35, "ui-white-squircle-corners"),

    SQUIRCLE_16_BORDER(16, "ui-white-squircle-border"),
    SQUIRCLE_20_BORDER(20, "ui-white-squircle-border"),
    SQUIRCLE_25_BORDER(25, "ui-white-squircle-border"),
    SQUIRCLE_35_BORDER(35, "ui-white-squircle-border"),
    SQUIRCLE_30_BORDER(30, "ui-white-squircle-border"),
    SQUIRCLE_40_BORDER(40, "ui-white-squircle-border"),
    SQUIRCLE_42_BORDER(42, "ui-white-squircle-border"),
    SQUIRCLE_50_BORDER(50, "ui-white-squircle-border"),

    SQUIRCLE_35_BORDER_TOP(35, "ui-white-squircle-border-top"),
    SQUIRCLE_50_BORDER_TOP(50, "ui-white-squircle-border-top"),
    SQUIRCLE_62_BORDER_TOP(62, "ui-white-squircle-border-top"),
    SQUIRCLE_35_BORDER_RIGHT(35, "ui-white-squircle-border-right"),
    SQUIRCLE_35_BORDER_BOTTOM(35, "ui-white-squircle-border-bottom"),
    SQUIRCLE_62_BORDER_BOTTOM(62, "ui-white-squircle-border-bottom"),

    SQUIRCLE_25_BTM(25, "ui-white-squircle-bottom"),
    SQUIRCLE_35_BTM(35, "ui-white-squircle-bottom"),
    SQUIRCLE_40_BTM(40, "ui-white-squircle-bottom"),
    SQUIRCLE_50_BTM(50, "ui-white-squircle-bottom"),
    SQUIRCLE_95_BOTTOM(95, "ui-white-squircle-bottom"),

    SQUIRCLE_20_TOP(20, "ui-white-squircle-top"),
    SQUIRCLE_35_TOP(35, "ui-white-squircle-top"),
    SQUIRCLE_50_TOP(50, "ui-white-squircle-top"),
    SQUIRCLE_62_TOP(62, "ui-white-squircle-top"),

    SQUIRCLE_25_LEFT(25, "ui-white-squircle-left"),
    SQUIRCLE_35_LEFT(35, "ui-white-squircle-left"),

    SQUIRCLE_25_RIGHT(25, "ui-white-squircle-right"),
    SQUIRCLE_35_RIGHT(35, "ui-white-squircle-right"),

    SQUIRCLE_35_BOTTOM_LEFT(35, "ui-white-squircle-bottom-left"),
    SQUIRCLE_35_BOTTOM_RIGHT(35, "ui-white-squircle-bottom-right"),

    ;

    @Getter
    private final int radius;
    private final String name;

    Squircle(int radius, String name) {
        this.radius = radius;
        this.name = name;
    }

    public String getRegionName() {
        return "ui/" + name + "-" + radius;
    }

    public Drawable getDrawable() {
        return getDrawable(Color.WHITE);
    }

    public Drawable getDrawable(Color color) {
        return Resources.getDrawable("basics/" + name + "-" + radius, color);
    }

    public static Drawable getSquircle (int radius, Color color) {
        return Resources.getDrawable("basics/ui-white-squircle-" + radius, color);
    }

    public static Drawable getSquircleBtm (int radius, Color color) {
        return Resources.getDrawable("basics/ui-white-squircle-bottom-" + radius, color);
    }

    public static Drawable getBorder (int radius, Color color) {
        return Resources.getDrawable("basics/ui-white-squircle-border-" + radius, color);
    }

    public static Drawable getBorderRight (int radius, Color color) {
        return Resources.getDrawable("basics/ui-white-squircle-border-right" + radius, color);
    }

    public static Drawable getLeaf (int radius, Color color) {
        return Resources.getDrawable("basics/ui-white-leaf-" + radius, color);
    }
}
