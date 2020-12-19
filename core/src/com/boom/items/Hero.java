package com.boom.items;

import com.boom.Config;

public class Hero extends Mob {

    public Backpack backpack;

    public Hero() {
        health = 100;
        name = Config.Entity.HERO_NAME;
    }
}
