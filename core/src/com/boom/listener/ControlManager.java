package com.boom.listener;

import java.util.LinkedList;
import java.util.List;

public class ControlManager {

    private static ControlManager instance;

    private List<ControlEvent> events;
    private Control control;

    private ControlManager() {
        events = new LinkedList<>();
    }

    public static ControlManager getInstance() {
        if (instance == null) {
            instance = new ControlManager();
        }

        return instance;
    }

    public void addListener(ControlEvent event) {
        events.add(event);
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

    public void changeRun(int x, int y) {
        for (ControlEvent event: events)
            event.run(x, y);
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
