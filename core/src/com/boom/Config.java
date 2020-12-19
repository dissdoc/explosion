package com.boom;

public class Config {

    public static final String TITLE = "Boom!";
    public static final String VERSION = "0.1";

    public static final float WORLD_WIDTH = 1280f;
    public static final float WORLD_HEIGHT = 720f;

    public static final float FIT = 1.0f;

    public static final float PPM = 64f;

    // Map
    public static final String MAP_FILE = "map.tmx";

    public static final class Entity {

        public static final String HERO_TEXTURE = "hero.png";
        public static final String HERO_NAME = "Hero";
        public static final short HERO_MASK = 0x0001;
    }

    public static final class Item {
        public static final String AID_TEXTURE = "aid.png";
        public static final String AID_NAME = "aid";
        public static final short AID_MASK = 0x0002;
    }

    public static final class Obstacle {

        public static final String FLOOR_NAME = "Floor";
        public static final String FLOOR_LAYER = "Physics_Platform_Static";
        public static final String ITEMS_NAME = "Aid";
        public static final String ITEMS_LAYER = "Physics_Items";
    }
}
