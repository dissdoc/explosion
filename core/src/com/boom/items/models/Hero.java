package com.boom.items.models;

import com.badlogic.gdx.math.Vector2;
import com.boom.Config;
import com.boom.listener.ControlManager;

public class Hero extends Mob
    implements ControlManager.ControlEvent, ControlManager.DirectionEvent {

    private boolean isLeftSide = false;

    public Backpack backpack;

    public Hero(Vector2 pos) {
        super();
        health = 100;
        name = Config.Entity.HERO_NAME;
        position.casting(pos);
    }

    @Override
    public void run(int x, int y) {
        position.x = x;
        position.y = y;

        state = isLeftSide ? State.RUN_LEFT : State.RUN_RIGHT;
    }

    @Override
    public void idle() {
        state = isLeftSide ? State.IDLE_LEFT : State.IDLE_RIGHT;
    }

    @Override
    public void shoot() {
        state = isLeftSide ? State.SHOOT_LEFT : State.SHOOT_RIGHT;
    }

    public boolean canMove() {
        return state != State.IDLE_LEFT && state != State.IDLE_RIGHT
                && state != State.SHOOT_LEFT && state != State.SHOOT_RIGHT;
    }

    @Override
    public void climb() {
        state = State.CLIMB;
    }

    @Override
    public void turnLeft() {
        isLeftSide = true;
    }

    @Override
    public void turnRight() {
        isLeftSide = false;
    }
}
