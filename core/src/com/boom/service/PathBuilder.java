package com.boom.service;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.boom.Config;
import com.boom.domain.GameManager;
import com.boom.domain.GameWorld;
import com.boom.listener.ControlManager;
import com.boom.utils.MapBuilder;
import com.boom.utils.PathFinder;

import java.util.Stack;

public class PathBuilder {

    private MapBuilder mapBuilder;
    private Texture texture;

    private Stack<PathFinder.Cell> path;
    private boolean isFollowPath = false;

    public PathBuilder(MapBuilder mapBuilder) {
        this.mapBuilder = mapBuilder;
        this.texture = GameManager.getInstance().getManager().get(Config.SystemHud.PATH_SELECTED);

        path = new Stack<>();
    }

    public void drawHeroPath(float x, float y) {
        if (!ControlManager.getInstance().canDraw() || isFollowPath) {
            if (path.size() > 0) path = new Stack<>();
            return;
        }

        PathFinder.Cell hero = new PathFinder.Cell(
                mapBuilder.getPrimitive().getHero().getPosX(),
                mapBuilder.getHeight() - mapBuilder.getPrimitive().getHero().getPosY());
        PathFinder.Cell cursor = new PathFinder.Cell((int) x, mapBuilder.getHeight() - (int) y);

        path = PathFinder.getPath(mapBuilder.getMap(), hero, cursor);

        for (PathFinder.Cell cell: path) {
            GameWorld.getInstance()
                    .getBatch()
                    .draw(texture, cell.x, mapBuilder.getMap()[0].length - cell.y, 1, 1);
        }
    }

    public void followPath()  {
        if (path.size() > 0 && ControlManager.getInstance().canDraw()) {
            isFollowPath = true;

            RouteManager.getInstance().setPath(path, mapBuilder.getHeight());
            ControlManager.getInstance().changeRun(0, 0);
        }
    }

    public void checkerPath() {
        if (ControlManager.getInstance().canShoot()
                && ControlManager.getInstance().canSwitchControl()
                && !RouteManager.getInstance().isEmpty()) {
            RouteManager.getInstance().dispose();
        }

        if (RouteManager.getInstance().isEmpty()) {
            isFollowPath = false;
        }
    }
}
