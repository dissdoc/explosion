package com.boom.items.models;

import com.badlogic.gdx.math.Vector2;
import com.boom.Config;

public class Hero extends Mob {

    public Backpack backpack;

    public Hero(Vector2 pos) {
        super();
        health = 100;
        name = Config.Entity.HERO_NAME;
        position.casting(pos);
    }

    public float getJumpImpulse() {
        return 5f;
    }

    public void run(int x, int y) {
        position.x = x;
        position.y = y;

        state = State.RUN;
    }

    public void idle() {
        state = State.IDLE;
    }

    public void shoot() {
        state = State.SHOOT;
    }

    public void climb() {
        state = State.CLIMB;
    }
}
