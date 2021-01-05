package com.boom.service;

import com.badlogic.gdx.math.Vector2;
import com.boom.listener.ControlManager;
import com.boom.utils.PathFinder;

import java.util.Stack;

public class RouteManager {

    private static RouteManager instance;
    private Stack<PathFinder.Cell> path;
    private int DELTA_HEIGHT;

    private RouteManager() { }

    public static RouteManager getInstance() {
        if (instance == null) {
            instance = new RouteManager();
        }

        return instance;
    }

    public void setPath(Stack<PathFinder.Cell> path, int delta) {
        this.path = path;
        DELTA_HEIGHT = delta;
    }

    public void dispose() {
        this.path = new Stack<>();
    }

    public boolean isEmpty() {
        return path == null || path.isEmpty();
    }

    public Vector2 nextPosition(int x, int y) {
        if (path.isEmpty()) {
            ControlManager.getInstance().changeIdle();
            ControlManager.getInstance().lockControl(false);
            return null;
        }

        PathFinder.Cell cell = path.pop();
        Vector2 route = changeDirection(cell, x, y);

        if (route.x != 0.0f || route.y != 0.0f) {
            path.add(cell);
        }

        ControlManager.getInstance().lockControl(true);
        ControlManager.getInstance().changeRun(cell.x, DELTA_HEIGHT - cell.y);
        ControlManager.getInstance().changeDirection(cell.direction);

        return route;
    }

    private Vector2 changeDirection(PathFinder.Cell cell, int x, int y) {
        Vector2 route = new Vector2(0.0f, 0.0f);
        if (cell.direction == PathFinder.Direction.RIGHT && cell.x > x)
            route.x = 0.025f;
        else if (cell.direction == PathFinder.Direction.LEFT && cell.x <= x)
            route.x = -0.025f;
        else if (cell.direction == PathFinder.Direction.TOP && (DELTA_HEIGHT - cell.y) > y)
            route.y = 0.025f;
        else if (cell.direction == PathFinder.Direction.BOTTOM && (DELTA_HEIGHT - cell.y) <= y)
            route.y = -0.025f;

        return route;
    }
}
