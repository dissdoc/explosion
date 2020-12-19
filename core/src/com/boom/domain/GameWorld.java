package com.boom.domain;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.boom.listener.GameListener;

public class GameWorld {

    private static GameWorld instance;
    private World world;

    private GameWorld() { }

    public static GameWorld getInstance() {
        if (instance == null) {
            instance = new GameWorld();
        }

        return instance;
    }

    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(new GameListener());
    }

    public void update(float delta) {
        world.step(delta, 6, 2);
    }

    public void dispose() {
        world.dispose();
    }

    public World getWorld() {
        return world;
    }
}
