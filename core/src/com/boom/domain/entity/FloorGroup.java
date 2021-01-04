package com.boom.domain.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.boom.domain.GameWorld;

import static com.boom.utils.Converter.*;

public class FloorGroup {

    private final Rectangle rect;

    public FloorGroup(Rectangle rect) {
        this.rect = rect;
    }

    public void createBody() {
        float width = toUnits(rect.width);
        float height = toUnits(rect.height);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = GameWorld.getInstance().getWorld().createBody(bodyDef);

        BodyData data = new BodyData("Floor", width, height, null);
        body.setUserData(data);

        Fixture fixture = body.createFixture(shape, 1);
        fixture.setUserData(data);
        body.setTransform(toUnits(rect.x) + width / 2,
                toUnits(rect.y) + height / 2, 0);
    }
}
