package com.tonir.demo.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ObjectMap;

public class DrawableManager {

    private final Skin skin;
    private final ObjectMap<String, Drawable> drawableCache = new ObjectMap<>();
    private final ObjectMap<String, ObjectMap<Color, String>> drawableKeyCache = new ObjectMap<>();

    public DrawableManager (final Skin skin) {
        this.skin = skin;
    }

    public Drawable getDrawable (final String region, final Color color) {
        final String key = getKeyFromRegionColor(region, color);
        if (drawableCache.containsKey(key)) {
            return drawableCache.get(key);
        }
        if (!skin.has(region, TextureRegion.class)) {
            throw new IllegalArgumentException("region not found in ui skin: " + region);
        }
        final Drawable drawable = skin.newDrawable(region, color);
        drawableCache.put(key, drawable);
        return drawable;
    }

    public Drawable getDrawable (final String region) {
        return getDrawable(region, Color.WHITE);
    }

    private String getKeyFromRegionColor (final String region, final Color color) {
        if (!drawableKeyCache.containsKey(region)) {
            drawableKeyCache.put(region, new ObjectMap<>());
        }
        final ObjectMap<Color, String> colorMap = drawableKeyCache.get(region);
        if (!colorMap.containsKey(color)) {
            colorMap.put(color, region + "_" + color.toString());
        }
        return colorMap.get(color);
    }

    public void dispose () {
        drawableCache.clear();
        drawableKeyCache.clear();
    }
}
