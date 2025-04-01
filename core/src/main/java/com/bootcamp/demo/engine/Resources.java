package com.bootcamp.demo.engine;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import lombok.Getter;

public class Resources implements Disposable {

    @Getter
    private final AssetManager assetManager;

    @Getter
    private final Skin uiSkin;

    @Getter
    private final FontManager fontManager;
    @Getter
    private final DrawableManager drawableManager;

    public Resources () {
        // initialize asset manager and loaders
        final InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager = new AssetManager();
        assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));

        // load default assets
        assetManager.load("gameassets/gameatlas.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        final TextureAtlas atlas = assetManager.get("gameassets/gameatlas.atlas", TextureAtlas.class);
        uiSkin = new Skin(atlas);

        // initialize managers
        fontManager = new FontManager();
        fontManager.preloadFonts(GameFont.values());
        drawableManager = new DrawableManager(uiSkin);
    }

    // static facade method to get a BitmapFont from a Font
    public static Label.LabelStyle getLabelStyle (final GameFont font) {
        return API.get(Resources.class).getFontManager().getLabelStyle(font);
    }

    // static facade method to get a Drawable from a region and color
    public static Drawable getDrawable (final String region, final Color color) {
        return API.get(Resources.class).getDrawableManager().getDrawable(region, color);
    }

    // static facade method with default white color
    public static Drawable getDrawable (final String region) {
        return getDrawable(region, Color.WHITE);
    }

    @Override
    public void dispose () {
        assetManager.dispose();
        uiSkin.dispose();
        fontManager.dispose();
        drawableManager.dispose();
    }
}
