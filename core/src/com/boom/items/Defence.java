package com.boom.items;

public enum Defence {

    None(null, 0),
    Helmet("Defender#1", 2),
    Jaket("Defender#2", 3),
    Boots("Defender#3", 1);

    public final String name;
    public final int protection;

    private Defence(String name, int protection) {
        this.name = name;
        this.protection = protection;
    }
}
