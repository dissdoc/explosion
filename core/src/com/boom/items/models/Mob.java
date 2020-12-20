package com.boom.items.models;

import com.badlogic.gdx.math.Vector2;

public class Mob {

    public Vector2 position;
    public String name;
    public int health;
    public Armor gun = Armor.NONE;
    public Defence head = Defence.NONE;
    public Defence body = Defence.NONE;
    public Defence legs = Defence.NONE;
    public State state = State.IDLE;
}
