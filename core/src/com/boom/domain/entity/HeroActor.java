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
import com.boom.service.RouteManager;

import static com.boom.Config.*;

public class HeroActor extends Actor {

    private final static float DELTA_CENTER = .5f;

    private float animationTimer = 0;

    private TextureRegion currentFrame;
    private Animation<TextureRegion> idle_right;
    private Animation<TextureRegion> idle_left;
    private Animation<TextureRegion> walk_right;
    private Animation<TextureRegion> walk_left;
    private Animation<TextureRegion> shoot_right;
    private Animation<TextureRegion> shoot_left;
    private Animation<TextureRegion> climb;

    public Sprite sprite;
    public Body body;

    private final Hero hero;

    public HeroActor(Hero hero) {
        this.hero = hero;

        Texture texture = GameManager.getInstance().getManager().get(Entity.HERO_TEXTURE, Texture.class);
        TextureRegion[][] regions = TextureRegion.split(texture, TILE_SIZE, TILE_SIZE);

        renderIdleRight(regions[0]);
        renderIdleLeft(regions[0]);
        renderShootLeft(regions[1][0], regions[1][1], regions[1][2]);
        renderShootRight(regions[1][0], regions[1][1], regions[1][2]);
        renderWalkRight(regions[2]);
        renderWalkLeft(regions[2]);
        renderClimb(regions[3][0], regions[3][1], regions[3][2], regions[3][3], regions[3][4]);

        body = createBody(hero.name);
    }

    @Override
    public void act(float delta) {
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
        if (!hero.canMove()) return;

        Vector2 pos = body.getPosition();
        Vector2 route  =  RouteManager.getInstance()
                .nextPosition((int) (pos.x-.5f), (int) (pos.y-.5f));
        if (route != null) {
            body.setTransform(pos.x + route.x, pos.y + route.y, 0);
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

    private void renderIdleRight(TextureRegion... regions) {
        idle_right = new Animation<>(.25f, regions);
        idle_right.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void renderIdleLeft(TextureRegion... regions) {
        TextureRegion[] sprites = flipRegions(regions);
        idle_left = new Animation<>(.25f, sprites);
        idle_left.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void renderShootRight(TextureRegion... regions) {
        shoot_right = new Animation<>(.35f, regions);
        shoot_right.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void renderShootLeft(TextureRegion... regions) {
        TextureRegion[] sprites = flipRegions(regions);
        shoot_left = new Animation<>(.35f, sprites);
        shoot_left.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void renderWalkRight(TextureRegion... regions) {
        walk_right = new Animation<>(.15f, regions);
        walk_right.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void renderWalkLeft(TextureRegion... regions) {
        TextureRegion[] sprites = flipRegions(regions);
        walk_left = new Animation<>(.15f, sprites);
        walk_left.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void renderClimb(TextureRegion... regions) {
        climb = new Animation<>(.1f, regions);
        climb.setPlayMode(Animation.PlayMode.LOOP);
    }

    private TextureRegion[] flipRegions(TextureRegion... regions) {
        TextureRegion[] sprites = new TextureRegion[regions.length];
        for (int i = 0; i < regions.length; i++) {
            TextureRegion region = new TextureRegion(regions[i]);
            region.flip(true, false);
            sprites[i] = region;
        }

        return sprites;
    }

    private void drawFrames() {
        switch (hero.state) {
            case RUN_RIGHT:
                currentFrame = walk_right.getKeyFrame(animationTimer);
                break;
            case RUN_LEFT:
                currentFrame = walk_left.getKeyFrame(animationTimer);
                break;
            case IDLE_RIGHT:
                currentFrame = idle_right.getKeyFrame(animationTimer);
                break;
            case IDLE_LEFT:
                currentFrame = idle_left.getKeyFrame(animationTimer);
                break;
            case SHOOT_RIGHT:
                currentFrame = shoot_right.getKeyFrame(animationTimer);
                break;
            case SHOOT_LEFT:
                currentFrame = shoot_left.getKeyFrame(animationTimer);
                break;
            case CLIMB:
                currentFrame = climb.getKeyFrame(animationTimer);
                break;
        }
    }
}
