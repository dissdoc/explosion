package com.boom.domain.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        implements ActionHandler {

    private final static float DELTA_CENTER = .5f;

    private float animationTimer = 0;
    private TextureRegion currentFrame;
    private final Animation<TextureRegion> idle;

    public Sprite sprite;
    public Body body;

    private Hero hero;

    public HeroActor(Hero hero) {
        this.hero = hero;

        Texture texture = GameManager.getInstance().getManager().get(Entity.HERO_TEXTURE, Texture.class);
        TextureRegion[] idleRegions = TextureRegion.split(texture, TILE_SIZE, TILE_SIZE)[0];
        idle = new Animation<>(0.25f, idleRegions);
        idle.setPlayMode(Animation.PlayMode.LOOP);

        body = createBody(hero.name);
    }

    @Override
    public void act(float delta) {
        animationTimer += delta;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        currentFrame = idle.getKeyFrame(animationTimer);

        float posX = body.getPosition().x - DELTA_CENTER;
        float posY = body.getPosition().y - DELTA_CENTER;

        batch.draw(currentFrame, posX, posY, 1, 1);
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

    public Body createBody(String name) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(toCenterSprite(hero.position.x), toCenterSprite(hero.position.y));
        bodyDef.fixedRotation = true;

        Body body = GameWorld.getInstance().getWorld().createBody(bodyDef);
        body.setUserData(new BodyData(name, 1, 1, this));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(DELTA_CENTER, DELTA_CENTER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = .75f;
        fixtureDef.friction = .75f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);

        shape.dispose();

        return body;
    }

    private float toCenterSprite(float pos) {
        return pos + DELTA_CENTER;
    }
}
