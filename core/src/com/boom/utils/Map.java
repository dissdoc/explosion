package com.boom.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.boom.domain.entity.AidGroup;

import static com.boom.Config.*;

public class Map {

    public static void buildStaticFloor(TiledMap map) {
        BaseBody.create()
                .type(BodyDef.BodyType.StaticBody)
                .createObstaclesByName(map, Obstacle.FLOOR_LAYER, Obstacle.FLOOR_NAME);
    }

    public static void buildStaticAids(TiledMap map, Stage stage) {
        MapObjects objects = map.getLayers().get(Obstacle.ITEMS_LAYER).getObjects();
        for (MapObject object: objects) {
            RectangleMapObject rectMapObject = (RectangleMapObject) object;
            Rectangle rect = rectMapObject.getRectangle();

            AidGroup aid = new AidGroup();
            aid.setObjectPosition(rect.x, rect.y);
            stage.addActor(aid);
        }
    }
}
