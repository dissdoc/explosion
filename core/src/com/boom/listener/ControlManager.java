package com.boom.listener;

import java.util.LinkedList;
import java.util.List;

public class ControlManager {

    private static ControlManager instance;

    private List<ControlEvent> events;

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
        for (ControlEvent event: events)
            event.shoot();
        System.out.println("Event shoot");
    }

    public void changeRun() {
        for (ControlEvent event: events)
            event.run();
        System.out.println("Event run");
    }

    public interface ControlEvent {

        void shoot();

        void run();
    }

    public static class ControlListener implements ControlEvent {

        @Override
        public void shoot() {

        }

        @Override
        public void run() {

        }
    }
}
