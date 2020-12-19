package com.boom.domain.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.boom.items.Hero;
import com.boom.items.State;

public class HeroGroup extends Group {

    private static final float IMPULSE = 5f;

    private Hero hero;

    private HeroActor heroActor;
//    private HealthActor healthActor;

    public HeroGroup() {
        hero = new Hero();
        heroActor = new HeroActor(hero);

        this.addActor(heroActor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        int xSpeed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (!heroActor.isJump) {
                heroActor.isJump = true;
                hero.state = State.Jump;
                heroActor.jump(IMPULSE);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            hero.state = State.StandLeft;
            if (heroActor.isClimb)
            xSpeed = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            hero.state = State.StandRight;
            if(heroActor.isClimb)
            xSpeed = 1;
        }

        heroActor.fall(IMPULSE);
        heroActor.run(xSpeed);
    }

    public Vector2 getPosition() {
        return heroActor.getPosition();
    }

    public void checkOut(float mapWidth, float mapHeight) {
        heroActor.checkOut(mapWidth, mapHeight);
    }

    public void dispose() {

    }
}
