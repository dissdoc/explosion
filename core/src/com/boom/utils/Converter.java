package com.boom.utils;

import static com.boom.Config.PPM;

public class Converter {

    public static float toUnits(float pixels) {
        return pixels / PPM;
    }

    public static float toPixels(float units) {
        return units * PPM;
    }
}
