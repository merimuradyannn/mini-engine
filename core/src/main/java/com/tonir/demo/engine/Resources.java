package com.tonir.demo.engine;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.tonir.demo.localization.Font;
import com.tonir.demo.managers.API;
import lombok.Getter;

public class Resources implements Disposable {

    @Getter
    private final AssetManager assetManager;

    @Getter
    private final Skin uiSkin;

    private final ObjectMap<String, Drawable> drawableCache = new ObjectMap<>();
    private final ObjectMap<String, ObjectMap<Color, String>> drawableKeyCache = new ObjectMap<>();

    public Resources () {
        // initialize asset manager and loaders
        final InternalFileHandleResolver internalFileHandleResolver = new InternalFileHandleResolver();
        this.assetManager = new AssetManager();
        this.assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(internalFileHandleResolver));

        // load default assets
        this.assetManager.load("gameassets/gameatlas.atlas", TextureAtlas.class);
        this.assetManager.finishLoading();

        // initialize UI skin with loaded atlas
        TextureAtlas atlas = this.assetManager.get("gameassets/gameatlas.atlas", TextureAtlas.class);
        this.uiSkin = new Skin(atlas);
    }

    public static Drawable getDrawable (String region, Color color) {
        return API.get(Resources.class).obtainDrawable(region, color);
    }

    public static Drawable getDrawable (String region) {
        return getDrawable(region, Color.WHITE);
    }

    // obtain a drawable with specified region and color
    public Drawable obtainDrawable (String region, Color color) {
        final String key = getKeyFromRegionColor(region, color);

        if (drawableCache.containsKey(key)) {
            return drawableCache.get(key);
        }

        if (!uiSkin.has(region, TextureRegion.class)) {
            throw new IllegalArgumentException("Region not found in UI skin: " + region);
        }

        final Drawable drawable = uiSkin.newDrawable(region, color);
        drawableCache.put(key, drawable);
        return drawable;
    }

    // obtain a drawable with a default fallback region
    public Drawable obtainDrawable (String region, Color color, String defaultRegion) {
        final String key = getKeyFromRegionColor(region, color);

        if (drawableCache.containsKey(key)) {
            return drawableCache.get(key);
        }

        final String regionToUse = uiSkin.has(region, TextureRegion.class) ? region : defaultRegion;

        if (!uiSkin.has(regionToUse, TextureRegion.class)) {
            throw new IllegalArgumentException("Neither region nor default region found in UI skin: " + regionToUse);
        }

        final Drawable drawable = uiSkin.newDrawable(regionToUse, color);
        drawableCache.put(key, drawable);
        return drawable;
    }

    // build a unique key based on region and color
    private String getKeyFromRegionColor (String region, Color color) {
        if (!drawableKeyCache.containsKey(region)) {
            drawableKeyCache.put(region, new ObjectMap<>());
        }

        ObjectMap<Color, String> colorMap = drawableKeyCache.get(region);
        if (!colorMap.containsKey(color)) {
            colorMap.put(color, region + "_" + color.toString());
        }

        return colorMap.get(color);
    }

    @Override
    public void dispose () {
        assetManager.dispose();
        uiSkin.dispose();
        drawableCache.clear();
        drawableKeyCache.clear();
    }

    public Label.LabelStyle getLabelStyle (Font font) {
        // TODO: 31.12.24 make
        return null;
    }
}
