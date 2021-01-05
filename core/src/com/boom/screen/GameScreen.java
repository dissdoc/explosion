package com.boom.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boom.domain.GameManager;
import com.boom.domain.GameWorld;
import com.boom.listener.CursorManager;
import com.boom.scene.HudStatus;
import com.boom.service.PathBuilder;
import com.boom.utils.MapBuilder;

import static com.boom.Config.*;
import static com.boom.utils.Converter.*;

public class GameScreen extends ScreenAdapter {

    private int MAP_WIDTH = 0;
    private int MAP_HEIGHT = 0;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    // Map loading
    private MapBuilder mapBuilder;
    private PathBuilder pathBuilder;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private CursorManager cursorManager;

    private Box2DDebugRenderer debugRender;

    private HudStatus hudStatus;
    private Stage stage;

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        cursorManager = new CursorManager();
        GameWorld.getInstance().show();
        debugRender = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        viewport = new FitViewport(toUnits(WORLD_WIDTH) * FIT, toUnits(WORLD_HEIGHT) * FIT, camera);
        viewport.apply(true);

        batch = new SpriteBatch();
        stage = new Stage(viewport);

        mapBuilder = new MapBuilder();
        mapBuilder.init(stage);
        pathBuilder = new PathBuilder(mapBuilder, batch);

        // Create menu game
        hudStatus = new HudStatus(batch);

        map = GameManager.getInstance().getManager().get(MAP_FILE);
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
        MAP_WIDTH = layer.getWidth();
        MAP_HEIGHT = layer.getHeight();
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/PPM, batch);
        mapRenderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        cursorManager.render();
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
    }

    private void draw(float delta) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        mapRenderer.render();

        batch.begin();

        Vector3 vec = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        pathBuilder.drawHeroPath(vec.x, vec.y);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            pathBuilder.followPath();
        }

        pathBuilder.checkerPath();

        batch.end();
        batch.setProjectionMatrix(hudStatus.stage.getCamera().combined);
        hudStatus.stage.draw();
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

        mapRenderer.setView(camera);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(.24f, .24f, .24f, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.graphics.setTitle(String.format("%s v.%s", TITLE, VERSION));
    }
}
