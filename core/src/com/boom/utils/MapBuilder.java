package com.boom.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.boom.domain.GameManager;
import com.boom.domain.entity.AidGroup;
import com.boom.domain.entity.HeroGroup;

import static com.boom.Config.*;
import static com.boom.utils.Converter.*;

public class MapBuilder {

    private TileType[][] map;
    private TiledMap tiledMap;

    public HeroGroup hero;

    public MapBuilder() {
        tiledMap = GameManager.getInstance().getManager().get(MAP_FILE);
    }

    public TileType[][] getMap() {
        return map;
    }

    public void init() {
        TiledMapTileLayer layer =
                (TiledMapTileLayer) tiledMap.getLayers().get(Obstacle.PLATFORM_LAYER);

        map = new TileType[layer.getWidth()][layer.getHeight()];
        for (int y = 0; y < layer.getHeight(); y++) {
            for  (int x = 0; x < layer.getWidth(); x++) {
                map[x][y] = TileType.Space;
            }
        }

        fillFloor();
        fillObstacles();
    }

    private void fillFloor() {
        MapObjects objects = tiledMap.getLayers().get(Obstacle.FLOOR_LAYER).getObjects();
        for (MapObject object: objects) {
            setPositionObject((RectangleMapObject) object, TileType.Floor);
        }
    }

    private void fillObstacles() {
        MapObjects objects = tiledMap.getLayers().get(Obstacle.OBSTACLES_LAYER).getObjects();
        for  (MapObject object: objects) {
            String type = object.getProperties().get("type").toString();
            if (type.equals(TileType.Ladder.name()))
                setPositionObject((RectangleMapObject) object, TileType.Ladder);
            if (type.equals(TileType.Door.name()))
                setPositionObject((RectangleMapObject) object, TileType.Door);
        }
    }

    private void setPositionObject(RectangleMapObject rectMap, TileType type) {
        Rectangle rect = rectMap.getRectangle();

        int width = (int) (rect.width / PPM);
        int height = (int) (rect.height / PPM);

        int posX = (int) toUnits(rect.x);
        int posY = (int) toUnits(rect.y);

        for (int x = posX; x < width + posX; x++) {
            for (int y = posY; y < height + posY; y++) {
                map[x][map[0].length - y] = type;
            }
        }
    }

    public void buildStaticFloor() {
        BaseBody.create()
                .type(BodyDef.BodyType.StaticBody)
                .createObstaclesByName(tiledMap, Obstacle.FLOOR_LAYER, Obstacle.FLOOR_NAME);
    }

    public void buildMobs(Stage stage) {
        MapObjects objects = tiledMap.getLayers().get(Obstacle.MOBS_LAYER).getObjects();
        for (MapObject object: objects) {
            String type = object.getProperties().get("type").toString();
            if (type.equals(Entity.HERO_NAME)) {
                Rectangle rect = ((RectangleMapObject)object).getRectangle();
                Vector2 pos = position(rect.x, rect.y);

                hero = new HeroGroup(pos);
                stage.addActor(hero);
            }
        }
    }

    public void buildStaticItems(Stage stage) {
        MapObjects objects = tiledMap.getLayers().get(Obstacle.ITEMS_LAYER).getObjects();
        for (MapObject object: objects) {
            RectangleMapObject rectMapObject = (RectangleMapObject) object;
            Rectangle rect = rectMapObject.getRectangle();

            AidGroup aid = new AidGroup();
            aid.setObjectPosition(rect.x, rect.y);
            stage.addActor(aid);
        }
    }
}
