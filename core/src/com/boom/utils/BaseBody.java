package com.boom.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.boom.domain.entity.BodyData;
import com.boom.domain.GameWorld;

import static com.boom.utils.Converter.*;

public class BaseBody {

    private BodyDef.BodyType type;

    public BaseBody() {

    }

    public static BaseBody create() {
        return new BaseBody();
    }

    public BaseBody type(BodyDef.BodyType type) {
        this.type = type;
        return this;
    }

    public void createObstaclesByName(TiledMap map, String name, String data) {
        MapObjects objects = map.getLayers().get(name).getObjects();
        createBody(objects, type, data);
    }

    private void createBody(MapObjects objects, BodyDef.BodyType type, String userData) {
        for (MapObject object: objects) {
            RectangleMapObject rectMapObject = (RectangleMapObject) object;
            Rectangle rect = rectMapObject.getRectangle();
            float width = toUnits(rect.width);
            float height = toUnits(rect.height);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / 2, height / 2);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = type;
            Body body = GameWorld.getInstance().getWorld().createBody(bodyDef);

            BodyData data = new BodyData(userData, width, height, null);
            body.setUserData(data);
            Fixture fixture = body.createFixture(shape, 1);
            fixture.setUserData(userData);
            body.setTransform(toUnits(rect.x) + width / 2, toUnits(rect.y) + height / 2, 0);
            shape.dispose();
        }
    }
}
