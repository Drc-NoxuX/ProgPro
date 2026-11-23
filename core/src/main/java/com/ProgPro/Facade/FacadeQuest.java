package com.ProgPro.Facade;

import com.ProgPro.Factory.Quest.Quest;
import com.ProgPro.Managers.QuestManager;

import java.util.List;

public class FacadeQuest {
    private QuestManager questManager;

    public FacadeQuest(){
        questManager = QuestManager.getInstance();
    }

    public void update() throws Exception {
        questManager.update();
    }

    public List<Quest> getActiveQuests() {
        return questManager.getQuests();
    }
}
