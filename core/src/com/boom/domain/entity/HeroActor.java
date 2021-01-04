package com.boom.domain.entity;

import com.badlogic.gdx.Gdx;
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
import com.boom.items.models.Mob;
import com.boom.items.models.State;
import com.boom.listener.ControlManager;
import com.boom.service.RouteManager;

import static com.boom.Config.*;

public class HeroActor extends Actor {

    private final static float DELTA_CENTER = .5f;

    private float animationTimer = 0;
    private float dt = 0;

    private TextureRegion currentFrame;
    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> shoot;
    private Animation<TextureRegion> climb;

    public Sprite sprite;
    public Body body;

    private Hero hero;

    public HeroActor(Hero hero) {
        this.hero = hero;

        Texture texture = GameManager.getInstance().getManager().get(Entity.HERO_TEXTURE, Texture.class);
        TextureRegion[][] regions = TextureRegion.split(texture, TILE_SIZE, TILE_SIZE);
        idle = new Animation<>(.25f, regions[0]);
        idle.setPlayMode(Animation.PlayMode.LOOP);

        shoot = new Animation<>(.35f, regions[1][0], regions[1][1], regions[1][2]);
        shoot.setPlayMode(Animation.PlayMode.LOOP);

        walk = new Animation<>(.15f, regions[2]);
        walk.setPlayMode(Animation.PlayMode.LOOP);

        body = createBody(hero.name);
    }

    @Override
    public void act(float delta) {
        dt = delta;
        animationTimer += delta;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        drawFrames();

        float posX = body.getPosition().x - DELTA_CENTER;
        float posY = body.getPosition().y - DELTA_CENTER;

        move();

        batch.draw(currentFrame, posX, posY, 1, 1);
    }

    public void jump(float value) {
        body.applyLinearImpulse(new Vector2(0, value), body.getPosition(), true);
    }

    public void fall(float value) {
        if (body.getLinearVelocity().y < 0f) {
            jump(-value * 0.1f);
        }
    }

    public void move() {
        if (hero.state != State.RUN) return;

        Vector2 pos = body.getPosition();
        Vector2 vector  =  RouteManager.getInstance().nextPosition((int) (pos.x-.5f), (int) (pos.y-.5f));
        if (vector != null) {
            body.setTransform(pos.x + vector.x, pos.y + vector.y, 0);
        }
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
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 1.0f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);

        shape.dispose();

        return body;
    }

    private float toCenterSprite(float pos) {
        return pos + DELTA_CENTER;
    }

    private void drawFrames() {
        switch (hero.state) {
            case RUN:
                currentFrame = walk.getKeyFrame(animationTimer);
                break;
            case IDLE:
                currentFrame = idle.getKeyFrame(animationTimer);
                break;
            case SHOOT:
                currentFrame = shoot.getKeyFrame(animationTimer);
                break;
            case CLIMB:
                currentFrame = climb.getKeyFrame(animationTimer);
                break;
        }
    }
}
