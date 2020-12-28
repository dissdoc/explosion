package com.boom.domain.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public interface ActorComponent {

    Sprite createSprite(String texture);

    Vector2 getPosition();

}
