package com.boom.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.boom.domain.GameManager;
import com.boom.domain.GameWorld;
import com.boom.listener.ControlManager;

import java.util.LinkedList;
import java.util.List;

import static com.boom.Config.*;

public class HudStatus implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private SpriteBatch batch;

    private Label scoreLabel2;
    private Label worldTimer;

    public HudStatus() {
        batch = GameWorld.getInstance().getBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        AssetManager manager = GameManager.getInstance().getManager();

        scoreLabel2 = new Label(String.format("Score: %s", 10), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        worldTimer = new Label("", new Label.LabelStyle(new BitmapFont(), Color.GOLD));

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

        TableCommand commands = new TableCommand();
        commands.buildControls();

        stage.addActor(topMenu);
        stage.addActor(commands);
        // topMenu.debug();
    }

    public void draw() {
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
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

        Label text1 = new Label("100", new Label.LabelStyle(new BitmapFont(), Color.valueOf("f8d9a2")));
        text1.setAlignment(Align.center);

        status.add(text1).width(statusImage.getWidth()).padLeft(10);
        status.row();
        status.add(statusImage).width(statusImage.getWidth()).padLeft(10);

        table.add(icon);
        table.add(status);

        return table;
    }
}

class TableCommand extends Table {

    private List<CommandButton> buttonGroup = new LinkedList<>();
    private TextureRegion[] regions;

    public TableCommand() {
        this.center().bottom();
        this.setFillParent(true);
        this.setTouchable(Touchable.enabled);

        this.regions = GameManager.getInstance().getRegions(SystemHud.COMMANDS);
    }

    public void buildControls() {
        final CommandButton shootBtn = new CommandButton(
                regions[COMMAND_SHOOT_D], regions[COMMAND_SHOOT_E],
                () -> ControlManager.getInstance().changeShoot());
        this.add(shootBtn).padRight(8).padBottom(PPM / 4);
        buttonGroup.add(shootBtn);

        final CommandButton walkBtn = new CommandButton(
                regions[COMMAND_WALK_D], regions[COMMAND_WALK_E],
                () -> ControlManager.getInstance().changeIdle());
        walkBtn.toggleSelect(true);
        this.add(walkBtn).padBottom(PPM / 4);
        buttonGroup.add(walkBtn);
    }

    private class CommandClickListener extends ClickListener {

        private CommandButton button;

        public CommandClickListener(CommandButton button) {
            this.button = button;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int count) {
            if (!ControlManager.getInstance().canSwitchControl())
                return false;

            for (CommandButton btn: buttonGroup) {
                if (btn.isSelected()) {
                    btn.toggleSelect(false);
                }
            }

            button.toggleSelect(true);

            return true;
        }
    }

    private class CommandButton extends ImageButton {

        private boolean isSelected = false;
        private final TextureRegionDrawable unselectRegion;
        private final TextureRegionDrawable selectRegion;
        private final ControlButtonHandler handler;

        public CommandButton(TextureRegion imageUp, TextureRegion imageDown,
                             ControlButtonHandler handler) {
            super((Drawable) null);
            unselectRegion = new TextureRegionDrawable(imageUp);
            selectRegion = new TextureRegionDrawable(imageDown);

            this.handler = handler;
            this.getStyle().imageUp = unselectRegion;

            this.addListener(new CommandClickListener(this));
        }

        /**
         * Метод переключения необходимого действия из группы кнопок действий
         * @param isClicked было ли нажатие на данную кнопку или мы просто сбрасываем курсор
         */
        public void toggleSelect(boolean isClicked) {
            if (isClicked) {
                handler.click();
            }

            swapImages();
            isSelected = !isSelected;
        }

        public boolean isSelected() {
            return isSelected;
        }

        private void swapImages() {
            if (!isSelected)
                this.getStyle().imageUp = selectRegion;
            else
                this.getStyle().imageUp = unselectRegion;
        }
    }

    /**
     * Слушатель нажатия кнопки в навигации действий пользователя
     */
    private interface ControlButtonHandler {
        void click();
    }
}
