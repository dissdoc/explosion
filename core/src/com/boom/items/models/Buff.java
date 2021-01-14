package com.boom.items.models;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Buff {

    AIDKIT("First aid kit", 10);

    public final String name;
    public final int points;

    private Buff(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public Map<String, String> getInfoData() {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("name", name);
        data.put("points", points + "");

        return data;
    }
}
