package com.boom.domain.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.boom.Config.*;

public class TestActor extends Actor {

    private final Texture texture;

    public TestActor() {
        texture = new Texture("aid.png");
        setSize(texture.getWidth()/PPM, texture.getHeight()/PPM);
        setPosition(3, 3);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("HEllo");
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                System.out.println("ENTER");
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                System.out.println("EXIT");
                super.exit(event, x, y, pointer, toActor);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
