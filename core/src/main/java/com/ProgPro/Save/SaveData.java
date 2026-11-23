package com.ProgPro.Save;

import java.util.List;
import java.util.Map;

public class SaveData {
    public int playerX;
    public int playerY;
    public int playerLife;
    public String currentRoom;
    public List<String[]> activeQuests;
    public List<String> deadEnemies;
    public Map<String, Integer> merchandStock;
    public Map<String, Integer> inventory;
    public int gold;

    public SaveData(){}
}
