package com.boom.items.models;

import com.boom.Config;

public class Hero extends Mob {

    public Backpack backpack;

    public Hero() {
        health = 100;
        name = Config.Entity.HERO_NAME;
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
