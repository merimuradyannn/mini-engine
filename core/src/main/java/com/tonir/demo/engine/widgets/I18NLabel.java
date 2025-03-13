package com.tonir.demo.engine.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;
import com.tonir.demo.localization.Font;
import com.tonir.demo.localization.Localization;
import com.tonir.demo.managers.API;
import com.tonir.demo.engine.Resources;

public class I18NLabel extends Label {

    private static final StringBuilder builder = new StringBuilder();

    private String key;

    private I18NLabel (CharSequence text, Skin skin) {
        super(text, skin);
        initWith(text);
    }

    private I18NLabel (CharSequence text, Skin skin, String styleName) {
        super(text, skin, styleName);
        initWith(text);
    }

    private I18NLabel (CharSequence text, Skin skin, String fontName, Color color) {
        super(text, skin, fontName, color);
        initWith(text);
    }

    private I18NLabel (CharSequence text, Skin skin, String fontName, String colorName) {
        super(text, skin, fontName, colorName);
        initWith(text);
    }

    public I18NLabel (CharSequence text, LabelStyle style) {
        super(text, style);
        initWith(text);
    }

    private void initWith (CharSequence key) {
        if (key != null && key.length() != 0 && key.charAt(0) == '@') {
            builder.setLength(0);
            builder.append(key, 1, key.length());
            this.key = builder.toString();
            super.setText(API.get(Localization.class).getTranslatedKey(this.key));
        } else {
            this.key = null;
        }
    }

    @Override
    public void setText (CharSequence key) {
        setText(key, true);
    }

    public void setText (CharSequence key, boolean translate) {
        if (!translate) {
            super.setText(key);
            return;
        }
        initWith(key);
        CharSequence translatedKey = key;
        if (this.key != null) {
            translatedKey = API.get(Localization.class).getTranslatedKey(key);
        }
        super.setText(translatedKey);
    }

    public void setText (CharSequence key, CharSequence param) {
        initWith(key);
        CharSequence text = key;
        if (this.key != null) {
            text = API.get(Localization.class).format(this.key, param);
        }
        super.setText(text);
    }

    public void setText (CharSequence key, int param1, int param2) {
        initWith(key);
        CharSequence text = key;
        if (this.key != null) {
            text = API.get(Localization.class).format(this.key, param1, param2);
        }
        super.setText(text);
    }

    public void format (Object param) {
        super.setText(API.get(Localization.class).format(key, param));
    }

    public void format (Object param1, Object param2) {
        super.setText(API.get(Localization.class).format(key, param1, param2));
    }

    public void format (Object param1, Object param2, Object param3) {
        super.setText(API.get(Localization.class).format(key, param1, param2, param3));
    }

    public static I18NLabel make (Font font, Color color) {
        return make(font, color, null);
    }

    public static I18NLabel make (Font font, Color color, CharSequence text) {
        final Label.LabelStyle labelStyle = API.get(Resources.class).getLabelStyle(font);
        final I18NLabel label = new I18NLabel(text, labelStyle);
        label.setColor(color);
        return label;
    }
}
