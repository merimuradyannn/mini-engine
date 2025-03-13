package com.tonir.demo.engine;

import com.badlogic.gdx.utils.ObjectMap;

public class ColorLibrary {
    public static ObjectMap<String, com.badlogic.gdx.graphics.Color> colors = new ObjectMap<>();
    public static com.badlogic.gdx.graphics.Color get (String hashtag) {
        if (!colors.containsKey(hashtag)) {
            colors.put(hashtag, com.badlogic.gdx.graphics.Color.valueOf(hashtag));
        }
        return colors.get(hashtag);
    }
}
