package com.boom.domain;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.boom.listener.GameListener;
import com.boom.scene.HudInfoTooltip;

public class GameWorld {

    private static GameWorld instance;
    private static OrthographicCamera camera;
    private static SpriteBatch batch;
    private World world;

    /**
     * Hud elements
     */
    public static HudInfoTooltip tooltip;

    private GameWorld() { }

    public static GameWorld getInstance() {
        if (instance == null) {
            instance = new GameWorld();
            camera = new OrthographicCamera();
            batch = new SpriteBatch();

            tooltip = new HudInfoTooltip();
        }

        return instance;
    }

    public void show() {
        world = new World(new Vector2(0, /*-9.8f*/ 0.0f), true);
        world.setContactListener(new GameListener());
    }

    public void review() {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
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
