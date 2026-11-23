package com.ProgPro.GameEvent;

public interface GameEventObserver {
    public void displayEvent(GameEvent event, String message) throws Exception;
}
