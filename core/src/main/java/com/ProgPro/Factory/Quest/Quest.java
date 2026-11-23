package com.ProgPro.Factory.Quest;

import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;

public abstract class Quest {
    private String name;
    private String description;
    private boolean isCompleted;

    public Quest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public abstract boolean checkCompletion();

    public void complete() throws Exception {
        isCompleted = true;
        GameEventBus.getInstance().publish(GameEvent.PLAYER_WON, "Vous avez complété la quête : " + name);
    }

    public abstract String getType();
    public abstract String[] toSaveArray();
}
