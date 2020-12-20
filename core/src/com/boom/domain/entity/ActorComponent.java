package com.boom.domain.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public interface ActorComponent {

    Sprite createSprite(String texture);

    Body createBody(Sprite sprite, String name);
}
