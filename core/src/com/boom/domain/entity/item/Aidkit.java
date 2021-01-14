package com.boom.domain.entity.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.boom.domain.GameManager;
import com.boom.domain.entity.BaseEntity;
import com.boom.items.models.Buff;
import com.boom.listener.HoverListener;

import static com.boom.Config.*;
import static com.boom.utils.Converter.toUnits;

public class Aidkit extends BaseEntity {

    private final Texture texture;
    private final Buff influence;

    public Aidkit(float x, float y) {
        influence = Buff.AIDKIT;
        texture = GameManager.getInstance().getManager().get(Item.AID_TEXTURE, Texture.class);

        setSize(toUnits(texture.getWidth()), toUnits(texture.getHeight()));
        setPosition(x, y);

        addListener(new HoverListener(influence.getInfoData()));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}