package com.boom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boom.domain.GameWorld;
import com.boom.listener.CursorManager;
import com.boom.scene.HudStatus;
import com.boom.service.PathBuilder;
import com.boom.utils.MapBuilder;

import static com.boom.Config.*;
import static com.boom.utils.Converter.*;

public class GameScreen extends ScreenAdapter {

    private Viewport viewport;

    // Map loading
    private MapBuilder mapBuilder;
    private PathBuilder pathBuilder;

    private Box2DDebugRenderer debugRender;

    private HudStatus hudStatus;
    private Stage stage;

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        debugRender = new Box2DDebugRenderer();
        CursorManager.getInstance().init();
        GameWorld.getInstance().show();

        viewport = new FitViewport(
                toUnits(WORLD_WIDTH) * FIT,
                toUnits(WORLD_HEIGHT) * FIT,
                GameWorld.getInstance().getCamera());
        viewport.apply(true);

        stage = new Stage(viewport);

        mapBuilder = new MapBuilder();
        mapBuilder.init(stage);
        pathBuilder = new PathBuilder(mapBuilder);

        // Create menu game
        hudStatus = new HudStatus();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(hudStatus.stage);
        multiplexer.addProcessor(GameWorld.tooltip.stage);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        CursorManager.getInstance().render();
        update(delta);
        clearScreen();
        draw(delta);

        stage.act(delta);
        stage.draw();

        // Debug render as lines
//        debugRender.render(GameWorld.getInstance().getWorld(), camera.combined);
    }

    @Override
    public void dispose() {
        GameWorld.getInstance().dispose();
        hudStatus.dispose();
        GameWorld.tooltip.dispose();
    }

    private void draw(float delta) {
        GameWorld.getInstance().review();
        mapBuilder.render();

        GameWorld.getInstance().getBatch().begin();
        Vector3 vec = GameWorld.getInstance().getCamera().unproject(
                new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        pathBuilder.drawHeroPath(vec.x, vec.y);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            pathBuilder.followPath();
        }

        pathBuilder.checkerPath();

        GameWorld.getInstance().getBatch().end();

        hudStatus.draw();
        GameWorld.tooltip.draw(delta);
    }

    private void update(float delta) {
        GameWorld.getInstance().update(delta);

        hudStatus.update(delta);

        // ---------
//        if (!isDown) {
//            aid.setY(aid.getY() + 0.008f);
//        }
//        if (isDown) {
//            aid.setY(aid.getY() - 0.008f);
//        }
//
//        if (aid.getY() >= 4.2f) {
//            isDown = true;
//        } else if (aid.getY() < 4.0f) {
//            isDown = false;
//        }
        // =========

//        hero.checkOut(MAP_WIDTH, MAP_HEIGHT);
//        if (viewport.getWorldWidth() / 2 < hero.getPosition().x &&
//            MAP_WIDTH - viewport.getWorldWidth() / 2 > hero.getPosition().x) {
//            camera.position.x = hero.getPosition().x;
//            camera.update();
//        }

        mapBuilder.update();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(.24f, .24f, .24f, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.graphics.setTitle(String.format("%s v.%s", TITLE, VERSION));
    }
}
