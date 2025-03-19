package com.tonir.demo.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ObjectMap;
import com.tonir.demo.localization.GameFont;

public class FontManager {

    private final ObjectMap<String, BitmapFont> fontCache = new ObjectMap<>();
    private final ObjectMap<String, Label.LabelStyle> labelStyleCache = new ObjectMap<>();

    public void preloadFonts (GameFont[] fonts) {
        fontCache.clear();
        labelStyleCache.clear();

        for (GameFont font : fonts) {
            String key = font.getFontName();
            BitmapFont bitmapFont = loadFont(font);
            fontCache.put(key, bitmapFont);
            labelStyleCache.put(key, createLabelStyle(bitmapFont));
        }
    }

    public Label.LabelStyle getLabelStyle (GameFont font) {
        String key = font.getFontName();
        if (!labelStyleCache.containsKey(key)) {
            throw new RuntimeException("label style not preloaded: " + key);
        }
        return labelStyleCache.get(key);
    }

    public void dispose () {
        for (BitmapFont font : fontCache.values()) {
            font.dispose();
        }
        fontCache.clear();
        labelStyleCache.clear();
    }

    protected Label.LabelStyle createLabelStyle (BitmapFont bitmapFont) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = Color.WHITE;
        return style;
    }

    protected BitmapFont loadFont (GameFont font) {
        // build file path from font type, e.g., "fonts/bold.ttf"
        String filePath = "fonts/" + font.getFontType() + ".ttf";
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(filePath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = font.getFontSize() * 2;
        parameter.kerning = true;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.hinting = FreeTypeFontGenerator.Hinting.Full;
        if (font.getFontType().contains("stroke")) {
            parameter.borderWidth = 4f;
            parameter.borderColor = Color.valueOf("8e1a5bff");
        }
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();
        return bitmapFont;
    }
}
