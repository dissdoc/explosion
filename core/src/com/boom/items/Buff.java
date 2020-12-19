package com.boom.items;

public enum Buff {

    Aidkit("First aid kit", 10);

    public final String name;
    public final int points;

    private Buff(String name, int points) {
        this.name = name;
        this.points = points;
    }
}
