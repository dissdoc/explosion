package com.boom.domain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameManager {

    private static GameManager instance;
    private static TextureRegion[] regions;
    private AssetManager manager;

    private GameManager() {
        manager = new AssetManager();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public void init() {
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(
                        new InternalFileHandleResolver()
                )
        );
    }

    public AssetManager getManager() {
        return manager;
    }

    public TextureRegion[] getRegions(String filename) {
        if (regions == null || regions.length == 0) {
            regions = TextureRegion.split(manager.get(filename, Texture.class), 32, 32)[0];
        }

        return regions;
    }
}
