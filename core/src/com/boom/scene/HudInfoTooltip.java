package com.boom.scene;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boom.domain.GameManager;
import com.boom.domain.GameWorld;
import com.boom.listener.FontManager;

import java.util.Map;

import static com.boom.Config.*;
import static com.boom.utils.Converter.toUnits;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class HudInfoTooltip implements Disposable {

    public Stage stage;
    private final InfoTooltip tooltip;

    public HudInfoTooltip() {
        Viewport viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        tooltip = new InfoTooltip();
        stage.addActor(tooltip);
        stage.addAction(alpha(0f));

        tooltip.debug();
    }

    public void hide() {
        stage.addAction(fadeOut(.15f));
    }

    public void show() {
        stage.addAction(fadeIn(.25f));
    }

    public void setPosition(float x, float y) {
        tooltip.setPosition(x, y);
    }

    public void setText(Map<String, String> data) {
        tooltip.setInfo(data);
    }

    public void draw(float delta) {
        stage.act(delta);
        GameWorld.getInstance().getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private static class InfoTooltip extends Group {

        private final Frame frame;
        private final Text text;

        public InfoTooltip() {
            frame = new Frame();
            text = new Text();

            addActor(frame.getInstance());
            addActor(text.getInstance());

            setPosition(0, 0);
        }

        public void setInfo(Map<String, String> data) {
            text.setText(data);
        }

        public float getWidth() {
            return frame.width;
        }

        public float getHeight() {
            return frame.height;
        }

    }

    private static class Frame extends Actor {

        private final Image box;

        public final float width;
        public final float height;

        public Frame() {
            Texture texture = GameManager.getInstance().getManager().get(SystemHud.INFOBOX, Texture.class);
            box = new Image(texture);
            width = toUnits(texture.getWidth());
            height = toUnits(texture.getHeight());

            setSize(width, height);
        }

        public Image getInstance() {
            return box;
        }
    }

    private static class Text extends Actor {

        private final BitmapFont font;
        private final Label label;

        public Text() {
            font = FontManager.getInstance().getDefault();

            Label.LabelStyle style = new Label.LabelStyle(font, null);
            label = new Label("", style);
            label.setPosition(10, 64 - label.getHeight() - 4);
        }

        public void setText(Map<String, String> data) {
            String info = String.format("%s\n+%s points",
                    data.get("name"),
                    data.get("points"));
            label.setText(info);
        }

        public Label getInstance() {
            return label;
        }
    }
}
