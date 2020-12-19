package com.boom.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.boom.Config.*;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Label fpsLabel;
    private Label scoreLabel;
    private Label worldTimer;

    public Hud(SpriteBatch batch) {
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT,
                new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        fpsLabel = new Label("",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("Score: %s", 10),
                new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        worldTimer = new Label("",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(fpsLabel).expandX().padTop(5);
        table.add(scoreLabel).expandX().padTop(5);

        Table table2 = new Table();
        table2.bottom();
        table2.setFillParent(true);

        table2.add(worldTimer).expandX().padBottom(10);

        stage.addActor(table);
        stage.addActor(table2);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float delta) {
        fpsLabel.setText(String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()));
        worldTimer.setText(String.format("%s", delta));
    }
}
