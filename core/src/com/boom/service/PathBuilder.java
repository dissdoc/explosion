package com.boom.service;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boom.Config;
import com.boom.domain.GameManager;
import com.boom.utils.MapBuilder;
import com.boom.utils.PathFinder;

import java.util.List;

public class PathBuilder {

    private MapBuilder mapBuilder;
    private SpriteBatch batch;
    private Texture texture;

    private int MAP_HEIGHT = 0;

    public PathBuilder(MapBuilder mapBuilder, SpriteBatch batch) {
        this.mapBuilder = mapBuilder;
        this.batch = batch;
        this.texture = GameManager.getInstance().getManager().get(Config.SystemHud.PATH_SELECTED);

        MAP_HEIGHT = mapBuilder.getMap()[0].length;
    }

    public void drawHeroPath(float x, float y) {
        PathFinder.Cell hero = new PathFinder.Cell(
                mapBuilder.hero.mediator.hero.getX(),
                MAP_HEIGHT - mapBuilder.hero.mediator.hero.getY());
        PathFinder.Cell cursor = new PathFinder.Cell((int) x, MAP_HEIGHT - (int) y);

        List<PathFinder.Cell> path = PathFinder.getPath(mapBuilder.getMap(), hero, cursor);

        for (PathFinder.Cell cell :path) {
            batch.draw(texture, cell.x, mapBuilder.getMap()[0].length - cell.y, 1, 1);
        }
    }
}
