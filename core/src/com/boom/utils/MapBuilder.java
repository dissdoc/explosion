package com.boom.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.boom.domain.GameManager;
import com.boom.domain.GameWorld;
import com.boom.domain.entity.FloorGroup;
import com.boom.domain.entity.HeroGroup;
import com.boom.domain.entity.item.Aidkit;

import static com.boom.Config.*;
import static com.boom.utils.Converter.*;

public class MapBuilder {

    private TileType[][] map;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Primitive primitive;

    public MapBuilder() {
        tiledMap = GameManager.getInstance().getManager().get(MAP_FILE);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/PPM, new SpriteBatch());
        mapRenderer.setView(GameWorld.getInstance().getCamera());
    }

    public TileType[][] getMap() {
        return map;
    }

    public int getWidth() {
        return map.length;
    }

    public int getHeight() {
        return map[0].length;
    }

    public Primitive getPrimitive() {
        return primitive;
    }

    public void init(Stage stage) {
        fillSpace();
        fillFloor();
        fillObstacles();

        primitive = new Primitive(stage);
        primitive.init();

//        for (int y = 0; y < map[0].length; y++) {
//            for (int x = 0; x < map.length; x++) {
//                System.out.print(map[x][y].name().substring(0, 1) + " ");
//            }
//            System.out.println("");
//        }
    }

    public void render() {
        mapRenderer.render();
    }

    public void update() {
        mapRenderer.setView(GameWorld.getInstance().getCamera());
    }

    private void fillSpace() {
        TiledMapTileLayer layer =
                (TiledMapTileLayer) tiledMap.getLayers().get(Obstacle.PLATFORM_LAYER);

        map = new TileType[layer.getWidth()][layer.getHeight()];
        for (int y = 0; y < layer.getHeight(); y++) {
            for  (int x = 0; x < layer.getWidth(); x++) {
                map[x][y] = TileType.Space;
            }
        }
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

        int width = toCells(rect.width);
        int height = toCells(rect.height);

        int posX = toCells(rect.x);
        int posY = toCells(rect.y);

        for (int x = posX; x < width + posX; x++) {
            for (int y = posY; y < height + posY; y++) {
                map[x][map[0].length - y] = type;
            }
        }
    }

    public class Primitive {

        private final Stage stage;

        private HeroGroup hero;

        public Primitive(Stage stage) {
            this.stage = stage;
        }

        public void init() {
            buildStaticFloor();
            buildStaticItems();
            buildMobs();
            buildWindow();
        }

        public HeroGroup getHero() {
            return hero;
        }

        private void buildStaticFloor() {
            MapObjects objects = tiledMap.getLayers().get(Obstacle.FLOOR_LAYER).getObjects();
            for (MapObject object: objects) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                FloorGroup group = new FloorGroup(rect);
                group.createBody();
            }
        }

        private void buildMobs() {
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

        private void buildStaticItems() {
            MapObjects objects = tiledMap.getLayers().get(Obstacle.ITEMS_LAYER).getObjects();
            for (MapObject object: objects) {
                String type = object.getProperties().get("type").toString();
                RectangleMapObject rectMapObject = (RectangleMapObject) object;
                Rectangle rect = rectMapObject.getRectangle();

                if (type.equals(Item.AID_NAME)) {
                    Aidkit aidkit = new Aidkit(rect.x, rect.y);
                    stage.addActor(aidkit);
                }
            }
        }

        private void buildWindow() {
//            InfoTooltip tooltip = new InfoTooltip(428, 364);
//            tooltip.show();
//            stage.addActor(tooltip);
        }
    }
}
