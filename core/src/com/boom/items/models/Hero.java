package com.boom.items.models;

import com.badlogic.gdx.math.Vector2;
import com.boom.Config;

public class Hero extends Mob {

    public Backpack backpack;

    public Hero(Vector2 pos) {
        super();
        health = 100;
        name = Config.Entity.HERO_NAME;
        this.position = pos;
    }

    public int getX() {
        return (int) this.position.x;
    }

    public int getY() {
        return (int) this.position.y;
    }

    public boolean isJump() {
        return state == State.JUMP;
    }

    public void jump() {
        state = State.JUMP;
    }

    public float getJumpImpulse() {
        return 5f;
    }
}
