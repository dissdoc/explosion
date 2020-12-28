package com.boom.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.boom.BoomGame;
import com.boom.domain.GameManager;

import static com.boom.Config.*;

public class LoadingScreen extends ScreenAdapter {

    private BoomGame game;

    public LoadingScreen(BoomGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        update();
    }

    @Override
    public void show() {
        AssetManager manager = GameManager.getInstance().getManager();

        manager.load(MAP_FILE, TiledMap.class);

        manager.load(Entity.HERO_TEXTURE, Texture.class);
        manager.load(Item.AID_TEXTURE, Texture.class);

        // Loading system textures
        manager.load(SystemHud.BOOT, Texture.class);
        manager.load(SystemHud.HEALTH, Texture.class);
        manager.load(SystemHud.HEALTH_STATUS, Texture.class);
        manager.load(SystemHud.COMMANDS, Texture.class);
        manager.load(SystemHud.PATH_SELECTED, Texture.class);
    }

    private void update() {
        if (GameManager.getInstance().getManager().update()) {
            game.setScreen(new GameScreen());
        }
    }
}
