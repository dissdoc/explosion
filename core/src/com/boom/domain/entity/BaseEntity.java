package com.boom.domain.entity;

import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.boom.utils.Converter.*;

public class BaseEntity extends Actor {

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(toCells(x), toCells(y));
    }
}
