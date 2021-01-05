package com.boom.listener;

import com.boom.utils.PathFinder;

import java.util.LinkedList;
import java.util.List;

public class ControlManager {

    private static ControlManager instance;

    private List<ControlEvent> events;
    private List<DirectionEvent> directions;
    private Control control;
    private boolean isSwitchControl = false;

    private ControlManager() {
        events = new LinkedList<>();
        directions = new LinkedList<>();
    }

    public static ControlManager getInstance() {
        if (instance == null) {
            instance = new ControlManager();
        }

        return instance;
    }

    public void addListenerControls(ControlEvent event) {
        events.add(event);
    }

    public void addListenerDirections(DirectionEvent event) {
        directions.add(event);
    }

    public void changeShoot() {
        control = Control.SHOOT;
        for (ControlEvent event: events)
            event.shoot();
    }

    public void changeIdle() {
        control = Control.IDLE;
        for (ControlEvent event: events)
            event.idle();
    }

    public boolean canDraw() {
        return control == Control.IDLE;
    }

    public boolean canShoot() {
        return control == Control.SHOOT;
    }

    public void lockControl(boolean flag) {
        isSwitchControl = flag;
    }

    public boolean canSwitchControl() {
        return !isSwitchControl;
    }

    public void changeRun(int x, int y) {
        for (ControlEvent event: events)
            event.run(x, y);
    }

    public void changeDirection(PathFinder.Direction direction) {
        if (direction == PathFinder.Direction.BOTTOM || direction == PathFinder.Direction.TOP)
            for(DirectionEvent event: directions)
                event.climb();

        else if (direction == PathFinder.Direction.LEFT)
            for (DirectionEvent event: directions)
                event.turnLeft();

        else if (direction == PathFinder.Direction.RIGHT)
            for (DirectionEvent event: directions)
                event.turnRight();
    }

    public interface DirectionEvent {

        void turnLeft();
        void turnRight();
        void climb();
    }

    public interface ControlEvent {

        void shoot();
        void idle();
        void run(int x, int y);
    }

    public enum Control {
        SHOOT, IDLE
    }
}
