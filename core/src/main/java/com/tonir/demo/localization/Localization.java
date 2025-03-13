package com.tonir.demo.localization;

import com.badlogic.gdx.utils.Disposable;

public class Localization implements Disposable {

    @Override
    public void dispose () {

    }

    public CharSequence format (String key, Object param) {
        // TODO: 30.12.24 for now return key, implement localization later
        return key;
    }

    public CharSequence format (String key, Object param1, Object param2) {
        // TODO: 30.12.24 for now return key, implement localization later
        return key;
    }

    public CharSequence format (String key, Object param1, Object param2, Object param3) {
        // TODO: 30.12.24 for now return key, implement localization later
        return key;
    }

    public CharSequence getTranslatedKey (CharSequence key) {
        // TODO: 30.12.24 for now return key, implement localization later
        return key;
    }
}
