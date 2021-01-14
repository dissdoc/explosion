package com.boom.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boom.domain.GameWorld;

import java.util.Map;

public class HoverListener extends ClickListener {

    private Map<String, String> data;
    private boolean onMoved;

    public HoverListener(Map<String, String> data) {
        this.data = data;
        onMoved = false;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        onMoved = true;
        GameWorld.tooltip.show();

        super.enter(event, x, y, pointer, fromActor);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        onMoved = false;
        GameWorld.tooltip.hide();

        super.exit(event, x, y, pointer, toActor);
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        if (onMoved) {

        }

        return super.mouseMoved(event, x, y);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        System.out.println("DOWN");
        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        System.out.println("UP");
        super.touchUp(event, x, y, pointer, button);
    }
}
