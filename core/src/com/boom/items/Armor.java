package com.boom.items;

public enum Armor {

    None(null, 0),
    Pistol("Gun#1", 5),
    Rifle("Gun#2",2);

    public final String name;
    private final int bullets;

    private Armor(String name, int bullets) {
        this.name = name;
        this.bullets = bullets;
    }
}
