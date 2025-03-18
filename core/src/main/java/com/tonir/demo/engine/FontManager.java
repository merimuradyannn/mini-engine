package com.tonir.demo.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ObjectMap;
import com.tonir.demo.localization.GameFont;

public class FontManager {

    private final ObjectMap<String, BitmapFont> fontCache = new ObjectMap<>();
    private final ObjectMap<String, Label.LabelStyle> labelStyleCache = new ObjectMap<>();

    // preload all fonts from the provided font array
    public void preloadFonts (GameFont[] fonts) {
        fontCache.clear();
        labelStyleCache.clear();

        for (GameFont font : fonts) {
            final String key = font.getFontName();
            final BitmapFont bitmapFont = loadFont(font);
            fontCache.put(key, bitmapFont);
            final Label.LabelStyle style = createLabelStyle(bitmapFont);
            labelStyleCache.put(key, style);
        }
    }

    // get label style from cache
    public Label.LabelStyle getLabelStyle (GameFont font) {
        final String key = font.getFontName();
        if (!labelStyleCache.containsKey(key)) {
            throw new RuntimeException("label style not preloaded: " + key);
        }
        return labelStyleCache.get(key);
    }

    // dispose all loaded fonts and styles
    public void dispose () {
        for (BitmapFont font : fontCache.values()) {
            font.dispose();
        }
        fontCache.clear();
        labelStyleCache.clear();
    }

    // load font from ttf file in fonts folder with improved quality settings
    private BitmapFont loadFont (GameFont font) {
        final String path = "fonts/roboto-" + font.getFontName() + ".ttf";
        final FileHandle ttfFile = Gdx.files.internal(path);
        if (!ttfFile.exists()) {
            throw new RuntimeException("ttf file not found: " + path);
        }
        final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(ttfFile);
        final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = font.getFontSize();

        parameter.kerning = true;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.hinting = FreeTypeFontGenerator.Hinting.Full;

        final BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();
        return bitmapFont;
    }

    // create label style with default white color
    private Label.LabelStyle createLabelStyle (BitmapFont bitmapFont) {
        final Label.LabelStyle style = new Label.LabelStyle();
        style.font = bitmapFont;
        style.fontColor = Color.WHITE;
        return style;
    }
}
