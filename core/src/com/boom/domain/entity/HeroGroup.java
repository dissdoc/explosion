package com.boom.domain.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.boom.items.models.Hero;
import com.boom.listener.ControlManager;

public class HeroGroup extends Group {

    private Hero hero;
    private HeroActor heroActor;

//    private HealthActor healthActor;

    public HeroGroup(Vector2 pos) {
        hero = new Hero(pos);
        heroActor = new HeroActor(hero);

        this.addActor(heroActor);

        ControlManager.getInstance().addListenerControls(hero);
        ControlManager.getInstance().addListenerDirections(hero);
    }

    public int getPosX() {
        return hero.position.x;
    }

    public int getPosY() {
        return hero.position.y;
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
        heroActor.checkOut(mapWidth, mapHeight);
    }

    public void dispose() {

    }
}
