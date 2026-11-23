package com.ProgPro.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class GameEventBus {

    private static GameEventBus instance;
    private final List<GameEventObserver> observers;

    private GameEventBus() {
        observers = new ArrayList<>();
    }

    public static GameEventBus getInstance() {
        if(instance == null) instance = new GameEventBus();
        return instance;
    }

    public void subscribe(GameEventObserver observer) {
        observers.add(observer);
    }
    public void unsubscribe(GameEventObserver observer) {
        observers.remove(observer);
    }

    public void publish(GameEvent event, String message) throws Exception {
        for(GameEventObserver observer : new ArrayList<>(observers)) {
            observer.displayEvent(event, message);
        }
    }
}
