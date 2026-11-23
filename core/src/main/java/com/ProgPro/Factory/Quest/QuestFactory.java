package com.ProgPro.Factory.Quest;

import com.ProgPro.Factory.Object.Room.Room;
import com.ProgPro.Managers.RessourceManager;

public class QuestFactory {
    public static Quest createQuest(String[] parameters) throws Exception {
        switch (parameters[0]) {
            case "slayall":
                SlayAllEnemiesQuest quest = new SlayAllEnemiesQuest(parameters[1], parameters[2]);
                for(int i = 3; i < parameters.length; i++) {
                    for(Room room : RessourceManager.getInstance().getRooms()) {
                        if(room.getName().equals(parameters[i])) {
                            quest.addRoom(room);
                        }
                    }
                }
                return quest;
            case "goTo":
                return new GoToQuest(parameters[1], parameters[2]);
            default:
                throw new IllegalArgumentException("Invalid quest type");
        }
    }
}
