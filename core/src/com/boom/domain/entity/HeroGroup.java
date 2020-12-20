package com.boom.domain.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.boom.items.models.Hero;

public class HeroGroup extends Group {

    private HeroMediator mediator;
//    private HealthActor healthActor;

    public HeroGroup() {
        mediator = new HeroMediator();
        mediator.hero = new Hero();
        mediator.heroActor = new HeroActor(mediator.hero);

        this.addActor(mediator.heroActor);
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
            mediator.jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            hero.state = State.IDLE;
//            if (heroActor.isClimb)
//            xSpeed = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            hero.state = State.IDLE;
//            if(heroActor.isClimb)
//            xSpeed = 1;
        }
//
//        heroActor.fall(IMPULSE);
//        heroActor.run(xSpeed);
    }

    public Vector2 getPosition() {
        return mediator.heroActor.getPosition();
    }

    public void checkOut(float mapWidth, float mapHeight) {
        mediator.heroActor.checkOut(mapWidth, mapHeight);
    }

    public void dispose() {

    }
}
