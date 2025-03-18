package com.tonir.demo.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.tonir.demo.localization.GameFont;

public class Labels {

    public static Label make (GameFont font, Color color, String text) {
        // Get the shared base style from Resources
        final Label label = new Label(text, Resources.getLabelStyle(font));
        label.setColor(color);
        return label;
    }

    public static Label make (GameFont font, Color color) {
        return make(font, color, "");
    }

    public static Label make (GameFont font, String text) {
        return make(font, Color.WHITE, text);
    }

    public static Label make (GameFont font) {
        return make(font, Color.WHITE, "");
    }
}
