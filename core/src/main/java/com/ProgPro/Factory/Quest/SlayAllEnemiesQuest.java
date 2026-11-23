package com.ProgPro.Factory.Quest;

import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.Room.Room;

import java.util.ArrayList;
import java.util.List;

public class SlayAllEnemiesQuest extends Quest {

    private List<Room> rooms = new ArrayList<>();

    public SlayAllEnemiesQuest(String name, String description) {
        super(name, description);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public boolean checkCompletion() {
        for(Room room : rooms) {
            for(Enemy enemy : room.getEnemys()) {
                if(enemy.isAlive()){
                    return false;
                }
            }
        }
        return true;
    }

    public List<String> getRoomsNames(){
        List<String> names = new ArrayList<>();
        for(Room room : rooms){
            names.add(room.getName());
        }
        return names;
    }

    @Override
    public String getType() {
        return "slayall";
    }

    @Override
    public String[] toSaveArray() {
        String[] saveData = new String[rooms.size() + 3];
        saveData[0] = getType();
        saveData[1] = getName();
        saveData[2] = getDescription();
        int i = 3;
        for(Room room : rooms){
            saveData[i] = room.getName();
            i++;
        }
        return saveData;
    }
}
