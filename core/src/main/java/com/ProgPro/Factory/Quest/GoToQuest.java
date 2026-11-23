package com.ProgPro.Factory.Quest;

public class GoToQuest extends Quest {

    public GoToQuest(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean checkCompletion() {
        return false;
    }

    @Override
    public String getType() {
        return "goTo";
    }

    @Override
    public String[] toSaveArray() {
        String[] saveData = new String[3];
        saveData[0] = getType();
        saveData[1] = getName();
        saveData[2] = getDescription();
        return saveData;
    }
}
