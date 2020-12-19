package com.boom.screen;

import com.badlogic.gdx.ScreenAdapter;
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
        GameManager.getInstance().getManager().load(MAP_FILE, TiledMap.class);

        GameManager.getInstance().getManager().load(Entity.HERO_TEXTURE, Texture.class);
        GameManager.getInstance().getManager().load(Item.AID_TEXTURE, Texture.class);
    }

    private void update() {
        if (GameManager.getInstance().getManager().update()) {
            game.setScreen(new GameScreen());
        }
    }
}
