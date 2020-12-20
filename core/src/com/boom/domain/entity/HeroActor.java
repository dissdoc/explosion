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
import com.boom.domain.GameManager;
import com.boom.domain.GameWorld;
import com.boom.items.models.Hero;
import com.boom.listener.ActionHandler;

import static com.boom.Config.*;
import static com.boom.utils.Converter.toUnits;

public class HeroActor extends Actor
        implements ActorComponent, ActionHandler {

    public static final short HERO_COLLISION =
            ~Item.AID_MASK;

    private static final float POS_X = 2f;
    private static final float POS_Y = 2.5f;

    public Sprite sprite;
    public Body body;

    public boolean isJump = false;
    public boolean isClimb = true;

    public HeroActor(Hero hero) {
        sprite = createSprite(Entity.HERO_TEXTURE);
        body = createBody(sprite, hero.name);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                body.getPosition().y - sprite.getHeight() / 2);
        sprite.draw(batch);
    }

    @Override
    public void jump(float value) {
        body.applyLinearImpulse(new Vector2(0, value), body.getPosition(), true);
    }

    @Override
    public void fall(float value) {
        if (body.getLinearVelocity().y < 0f) {
            jump(-value * 0.1f);
        }
    }

    @Override
    public void run(int speed) {
        body.setLinearVelocity(speed * 5, body.getLinearVelocity().y);
    }

    public void checkOut(float mapWidth, float mapHeight) {
        if (body.getPosition().x - sprite.getWidth() / 2 < 0) {
            body.applyForceToCenter(new Vector2(256f, 0), true);
        }
        if (body.getPosition().x + sprite.getWidth() / 2 > mapWidth) {
            body.applyForceToCenter(new Vector2(-256f, 0), true);
        }

    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    // Create component for the Hero ---------------------------------------------------------------

    @Override
    public Sprite createSprite(String texture) {
        Sprite sprite = new Sprite(GameManager.getInstance().getManager().get(
                texture, Texture.class));
        sprite.setSize(toUnits(sprite.getWidth()), toUnits(sprite.getHeight()));

        return sprite;
    }

    @Override
    public Body createBody(Sprite sprite, String name) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(POS_X, POS_Y);
        bodyDef.fixedRotation = true;

        Body body = GameWorld.getInstance().getWorld().createBody(bodyDef);
        body.setUserData(new BodyData(name, sprite.getWidth(), sprite.getHeight(), this));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = .75f;
        fixtureDef.friction = .75f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);

        shape.dispose();

        return body;
    }
}
