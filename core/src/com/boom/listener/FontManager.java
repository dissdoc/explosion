package com.boom.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontManager {

    private static FontManager instance;

    private static FreeTypeFontGenerator generator;
    private static FreeTypeFontGenerator.FreeTypeFontParameter parameters;

    private FontManager() {

    }

    public static FontManager getInstance() {
        if (instance == null) {
            instance = new FontManager();
            generator = new FreeTypeFontGenerator(Gdx.files.internal("RopaSans-Regular.ttf"));
            parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        }

        return instance;
    }

    public BitmapFont getDefault() {
        parameters.color = new Color(Color.valueOf("f4bd00"));
        parameters.size = 14;
        parameters.kerning = true;

        return generator.generateFont(parameters);
    }
}
