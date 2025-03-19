package com.tonir.demo.localization;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public enum GameFont implements IFont {
    BOLD_20("roboto-bold", 20),
    BOLD_22("roboto-bold", 22),
    BOLD_24("roboto-bold", 24),
    BOLD_28("roboto-bold", 28),
    BOLD_32("roboto-bold", 32),
    BOLD_36("roboto-bold", 36),
    BOLD_40("roboto-bold", 40),
    BOLD_48("roboto-bold", 48),
    BOLD_50("roboto-bold", 50),
    BOLD_60("roboto-bold", 60),
    BOLD_70("roboto-bold", 70)
    ;

    public static final ObjectMap<String, Array<GameFont>> typeMap = new ObjectMap<>();

    static {
        for (GameFont value : GameFont.values()) {
            String type = value.getFontType();
            if (!typeMap.containsKey(type)) {
                typeMap.put(type, new Array<>());
            }

            typeMap.get(type).add(value);
        }
    }

    private final String fontType;
    private final int fontSize;

    GameFont (String fontType, int fontSize) {
        this.fontType = fontType;
        this.fontSize = fontSize;
    }

    @Override
    public String getFontType () {
        return fontType;
    }

    @Override
    public int getFontSize () {
        return fontSize;
    }

    @Override
    public String getFontName () {
        return fontType + "-" + fontSize;
    }
}
