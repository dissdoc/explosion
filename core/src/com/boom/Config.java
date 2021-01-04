package com.boom;

public class Config {

    public static final String TITLE = "Explosion";
    public static final String VERSION = "0.1";

    public static final float WORLD_WIDTH = 1280f;
    public static final float WORLD_HEIGHT = 720f;

    public static final float FIT = 1.0f;

    public static final float PPM = 64f;
    public static final int TILE_SIZE = 64;

    public static final int COMMAND_SHOOT_D = 0;
    public static final int COMMAND_SHOOT_E = 1;
    public static final int COMMAND_WALK_D = 2;
    public static final int COMMAND_WALK_E = 3;

    // Map
    public static final String MAP_FILE = "map.tmx";

    public static final class SystemHud {

        public static final String BOOT = "boot.png";
        public static final String HEALTH = "health.png";
        public static final String HEALTH_STATUS = "health_status.png";
        public static final String COMMANDS = "commands.png";
        public static final String CURSOR_RUN =  "cursor_run.png";
        public static final String CURSOR_SHOOT =  "cursor_shoot.png";
        public static final String PATH_SELECTED = "path_selected.png";
    }

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

        public static final String PLATFORM_LAYER = "Platforms";
        public static final String FLOOR_LAYER = "Physics_Platform_Static";
        public static final String MOBS_LAYER = "Physics_Mobs";
        public static final String ITEMS_LAYER = "Physics_Items";
        public static final String OBSTACLES_LAYER = "Physics_Obstacles";
    }
}
