package com.tonir.demo.engine;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public enum GameFont implements Font {
    BOLD_20("bold", 20),
    BOLD_22("bold", 22),
    BOLD_24("bold", 24),
    BOLD_28("bold", 28),
    BOLD_32("bold", 32),
    BOLD_36("bold", 36),
    BOLD_40("bold", 40),
    BOLD_50("bold", 50),
    BOLD_60("bold", 60),
    BOLD_70("bold", 70),

    REGULAR_20("regular", 20),
    REGULAR_28("regular", 28),
    REGULAR_40("regular", 40),

    STROKED_18("stroked", 18),
    STROKED_20("stroked", 20),
    STROKED_22("stroked", 22),
    STROKED_24("stroked", 24),
    STROKED_28("stroked", 28),
    STROKED_32("stroked", 32),
    STROKED_36("stroked", 36),
    STROKED_40("stroked", 40),
    STROKED_50("stroked", 50),
    STROKED_70("stroked", 70),

    DYNANMIC("dynamic", 50),

    XNUMBER_36("xnumber", 36),
    XNUMBER_50("xnumber", 50),
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
    public String getFontType() {
        return fontType;
    }

    @Override
    public int getFontSize() {
        return fontSize;
    }

    @Override
    public String getFontName () {
        return fontType + "-" + fontSize;
    }
}
