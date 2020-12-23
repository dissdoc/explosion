package com.boom.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boom.domain.GameManager;

import static com.boom.Config.*;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Label scoreLabel2;
    private Label worldTimer;

    public Hud(SpriteBatch batch) {
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT,
                new OrthographicCamera());
        stage = new Stage(viewport, batch);

        AssetManager manager = GameManager.getInstance().getManager();

        scoreLabel2 = new Label(String.format("Score: %s", 10),
                new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        worldTimer = new Label("",
                new Label.LabelStyle(new BitmapFont(), Color.GOLD));

        Table topMenu = new Table();
        topMenu.top();
        topMenu.setFillParent(true);

        Table leftMenuStatus = new Table();
        topMenu.add(leftMenuStatus).expandX().left();

        Image healthIcon = new Image(manager.get(SystemHud.HEALTH, Texture.class));
        Image healtStatus = new Image(manager.get(SystemHud.HEALTH_STATUS, Texture.class));
        Table healthTable = statusTable(healthIcon, healtStatus, 75, 200);
        leftMenuStatus.add(healthTable).expandX().left();

        Image stepIcon = new Image(manager.get(SystemHud.BOOT, Texture.class));
        Image stepStatus = new Image(manager.get(SystemHud.HEALTH_STATUS, Texture.class));
        Table stepTable = statusTable(stepIcon, stepStatus, 20, 100);
        stepTable.padTop(5);
        leftMenuStatus.row();
        leftMenuStatus.add(stepTable);

        topMenu.add(scoreLabel2).expandX().right();
        topMenu.row();

        Table commands = new Table();
        commands.center().bottom();
        commands.setFillParent(true);
        commands.setTouchable(Touchable.enabled);
        commands.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(actor.getX());
            }
        });

        TextureRegion[] regions = GameManager.getInstance().getRegions(SystemHud.COMMANDS);
        commands.add(new Image(regions[COMMAND_SHOOT_E])).padRight(5);
        commands.add(new Image(regions[COMMAND_WALK_D]));
        commands.add(worldTimer).expandX().padBottom(10);

        stage.addActor(topMenu);
        stage.addActor(commands);

//        topMenu.debug();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float delta) {
        worldTimer.setText(String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()));
    }

    private Table statusTable(Image iconImage, Image statusImage, float current, float count) {
        Table table = new Table();
        table.top();

        Table icon = new Table();
        icon.center();
        icon.add(iconImage);

        Table status = new Table();
        status.top();

        Label text1 = new Label("100", new Label.LabelStyle(new BitmapFont(),
                Color.valueOf("f8d9a2")));
        text1.setAlignment(Align.center);

        status.add(text1).width(statusImage.getWidth()).padLeft(10);
        status.row();
        status.add(statusImage).width(statusImage.getWidth()).padLeft(10);

        table.add(icon);
        table.add(status);

        return table;
    }
}
