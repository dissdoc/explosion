package com.boom.items.models;

public enum Armor {

    NONE(null, 0),
    PISTOL("Gun#1", 5),
    RIFLE("Gun#2",2);

    public final String name;
    private final int bullets;

    private Armor(String name, int bullets) {
        this.name = name;
        this.bullets = bullets;
    }
}
