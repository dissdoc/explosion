package com.boom.domain.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.boom.domain.GameManager;
import com.boom.domain.GameWorld;

import static com.boom.Config.*;
import static com.boom.utils.Converter.toUnits;

public class AidGroup extends Group {

    public final AidActor aidActor;

    public AidGroup() {
        aidActor = new AidActor();

        this.addActor(aidActor);
    }

    public void setObjectPosition(float x, float y) {
        aidActor.setObjectPosition(x, y);
    }
}

class AidActor extends Actor {

    public final Sprite sprite;
    public final Body body;

    public AidActor() {
        sprite = createSprite(Item.AID_TEXTURE);
        body = createBody(sprite, Item.AID_NAME);
    }

    public void setObjectPosition(float x, float y) {
        body.setTransform(toUnits(x) + sprite.getWidth()/2,
                toUnits(y) + sprite.getHeight()/2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                body.getPosition().y - sprite.getHeight() / 2);
        sprite.draw(batch);
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public Sprite createSprite(String texture) {
        Sprite sprite = new Sprite(GameManager.getInstance().getManager().get(
                texture, Texture.class));
        sprite.setSize(toUnits(sprite.getWidth()), toUnits(sprite.getHeight()));

        return sprite;
    }

    public Body createBody(Sprite sprite, String name) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = false;

        Body body = GameWorld.getInstance().getWorld().createBody(bodyDef);
        body.setUserData(name);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = .75f;
//        fixtureDef.filter.categoryBits = bits;
//            fixtureDef.filter.maskBits = ~Config.Entity.HERO_MASK;
//        fixtureDef.filter.groupIndex = 0;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);

        shape.dispose();

        return body;
    }
}
