package com.tonir.demo.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Labels {

    public static Label createLabel(Font font, String text, Skin skin) {
        // Create a new label style with the generated BitmapFont
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getBitmapFont(font);
        return new Label(text, style);
    }

    private static BitmapFont getBitmapFont(Font font) {
        // If using the default font type, return a default BitmapFont
        if ("default".equalsIgnoreCase(font.getFontType())) {
            return new BitmapFont();
        } else {
            // Otherwise, load a custom font from the assets folder.
            // For example, assuming font files are in "assets/fonts/"
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/" + font.getFontName() + ".ttf")
            );
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = font.getFontSize();
            BitmapFont bitmapFont = generator.generateFont(parameter);
            generator.dispose();
            return bitmapFont;
        }
    }

    public static Label make (GameFont font, Color white) {
        return null;
    }
}
