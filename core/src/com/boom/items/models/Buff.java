package com.boom.items.models;

public enum Buff {

    AIDKIT("First aid kit", 10);

    public final String name;
    public final int points;

    private Buff(String name, int points) {
        this.name = name;
        this.points = points;
    }
}
