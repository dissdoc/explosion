package com.boom.domain.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.boom.items.models.Hero;

public class HeroGroup extends Group {

    public final HeroMediator mediator;
//    private HealthActor healthActor;

    public HeroGroup(Vector2 pos) {
        mediator = new HeroMediator();
        mediator.hero = new Hero(pos);
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
    }

    public void checkOut(float mapWidth, float mapHeight) {
        mediator.heroActor.checkOut(mapWidth, mapHeight);
    }

    public void dispose() {

    }
}
