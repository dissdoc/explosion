package com.boom.items.models;

import com.badlogic.gdx.math.Vector2;

public class Mob {

    public Position position = new Position();
    public String name;
    public int health;
    public Armor gun = Armor.NONE;
    public Defence head = Defence.NONE;
    public Defence body = Defence.NONE;
    public Defence legs = Defence.NONE;
    public State state = State.IDLE;

    public static class Position {
        public int x = 0;
        public int y = 0;

        public void casting(Vector2 position) {
            x = (int) position.x;
            y = (int) position.y;
        }
    }
}
