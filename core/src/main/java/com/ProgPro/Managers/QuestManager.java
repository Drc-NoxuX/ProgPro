package com.ProgPro.Managers;

import com.ProgPro.Factory.Quest.Quest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestManager {

    private static QuestManager instance;
    private List<Quest> quests;

    private QuestManager() {
        quests = new ArrayList<>();
    }

    public static QuestManager getInstance() {
        if(instance == null){
            instance = new QuestManager();
        }
        return instance;
    }

    public void addQuest(Quest quest){
        quests.add(quest);
    }

    public void removeQuest(Quest quest){
        quests.remove(quest);
    }

    public void update() throws Exception {
        Iterator<Quest> iterator = quests.iterator();
        while(iterator.hasNext()) {
            Quest quest = iterator.next();
            if(quest.checkCompletion()){
                quest.complete();
                iterator.remove();
            }
        }
    }

    public List<Quest> getQuests() {
        return quests;
    }
    public static void reset(){
        instance = null;
    }
}
