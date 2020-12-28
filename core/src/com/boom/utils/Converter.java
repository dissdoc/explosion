package com.boom.utils;

import com.badlogic.gdx.math.Vector2;

import static com.boom.Config.PPM;

public class Converter {

    public static float toUnits(float pixels) {
        return pixels / PPM;
    }

    public static float toPixels(float units) {
        return units * PPM;
    }

    public static Vector2 position(float x, float y) {
        return new Vector2(toUnits(x), toUnits(y));
    }
}
