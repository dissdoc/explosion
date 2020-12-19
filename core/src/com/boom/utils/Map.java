package com.boom.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;

import static com.boom.Config.*;

public class Map {

    public static void buildStaticFloor(TiledMap map) {
        BaseBody.create()
                .type(BodyDef.BodyType.StaticBody)
                .createObstaclesByName(map, Obstacle.FLOOR_LAYER, Obstacle.FLOOR_NAME);
    }

    public static Sprite buildStaticAids(TiledMap map) {
        Sprite sprite = BaseBody.createSprite(Item.AID_TEXTURE);

        BaseBody.create()
                .type(BodyDef.BodyType.StaticBody)
                .createItemsByName(map, sprite, Obstacle.ITEMS_LAYER, Obstacle.ITEMS_NAME,
                        Item.AID_MASK);

        return sprite;
    }
}
