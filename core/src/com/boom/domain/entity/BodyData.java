package com.boom.domain.entity;

public class BodyData {

    public final String name;
    public final float width;
    public final float height;
    public final Object def;

    public BodyData(String name, float width, float height, Object def) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.def = def;
    }
}
