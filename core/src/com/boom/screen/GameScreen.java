package com.boom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boom.domain.GameManager;
import com.boom.domain.GameWorld;
import com.boom.domain.entity.HeroGroup;
import com.boom.scene.Hud;
import com.boom.utils.Map;

import static com.boom.Config.*;
import static com.boom.utils.Converter.*;

public class GameScreen extends ScreenAdapter {

    private int MAP_WIDTH = 0;
    private int MAP_HEIGHT = 0;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    // Map loading
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Box2DDebugRenderer debugRender;

    private Hud hud;
    private HeroGroup hero;
    private Stage stage;

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        GameWorld.getInstance().show();
        debugRender = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        viewport = new FitViewport(toUnits(WORLD_WIDTH) * FIT, toUnits(WORLD_HEIGHT) * FIT, camera);
        viewport.apply(true);

        batch = new SpriteBatch();

        // Create menu game
        hud = new Hud(batch);

        map = GameManager.getInstance().getManager().get(MAP_FILE);
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
        MAP_WIDTH = layer.getWidth();
        MAP_HEIGHT = layer.getHeight();
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/PPM, batch);
        mapRenderer.setView(camera);

        stage = new Stage(viewport);
        hero = new HeroGroup();
        stage.addActor(hero);

        Map.buildStaticFloor(map);
        Map.buildStaticAids(map, stage);
    }

    @Override
    public void render(float delta) {
        update(delta);
        clearScreen();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            System.out.println("LEFT" + Gdx.input.getX() + " " + Gdx.input.getY());
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
            System.out.println("RIGHT");
        draw(delta);

        stage.act(delta);
        stage.draw();

        // Debug render as lines
        debugRender.render(GameWorld.getInstance().getWorld(), camera.combined);
    }

    @Override
    public void dispose() {
        hero.dispose();
        GameWorld.getInstance().dispose();
        hud.dispose();
    }

    private void draw(float delta) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        mapRenderer.render();

        batch.begin();

        batch.end();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    private void update(float delta) {
        GameWorld.getInstance().update(delta);

        hud.update(delta);

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

        hero.checkOut(MAP_WIDTH, MAP_HEIGHT);
        if (viewport.getWorldWidth() / 2 < hero.getPosition().x &&
            MAP_WIDTH - viewport.getWorldWidth() / 2 > hero.getPosition().x) {
            camera.position.x = hero.getPosition().x;
            camera.update();
        }

        mapRenderer.setView(camera);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(.24f, .24f, .24f, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.graphics.setTitle(String.format("%s v.%s", TITLE, VERSION));
    }
}
