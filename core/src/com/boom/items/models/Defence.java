package com.boom.items.models;

public enum Defence {

    NONE(null, 0),
    HELMET("Defender#1", 2),
    JAKET("Defender#2", 3),
    BOOTS("Defender#3", 1);

    public final String name;
    public final int protection;

    private Defence(String name, int protection) {
        this.name = name;
        this.protection = protection;
    }
}
