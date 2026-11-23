package com.ProgPro.Facade;

import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.PNJ.MerchandPNJ;
import com.ProgPro.Factory.Object.PNJ.PNJ;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Object.Room.Room;
import com.ProgPro.Factory.Object.Usable.ItemFactory;
import com.ProgPro.Factory.Object.Usable.Pickable;
import com.ProgPro.Factory.Quest.Quest;
import com.ProgPro.Factory.Quest.QuestFactory;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.GameEvent.GameEventObserver;
import com.ProgPro.Interaction.GameContactListener;
import com.ProgPro.Managers.QuestManager;
import com.ProgPro.Managers.RessourceManager;
import com.ProgPro.Save.SaveData;
import com.ProgPro.UI.PauseMenuUI;
import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacadeManager implements GameEventObserver {
    private FacadeWorld facadeWorld;
    private FacadeCombat facadeCombat;
    private FacadeRender facadeRender;
    private FacadeInteraction facadeInteraction;
    private FacadeQuest facadeQuest;
    private PauseMenuUI pauseMenuUI;

    public FacadeManager(Game game) throws Exception {
        facadeWorld = new FacadeWorld();
        facadeCombat = new FacadeCombat();
        pauseMenuUI = new PauseMenuUI(game, this);
        facadeRender = new FacadeRender(facadeCombat, RessourceManager.getInstance().getPlayer(), pauseMenuUI);
        facadeInteraction = new FacadeInteraction(facadeRender);
        facadeQuest = new FacadeQuest();
        RessourceManager.getInstance().getWorld().setContactListener(new GameContactListener(facadeCombat));
        GameEventBus.getInstance().subscribe(this);
    }

    public void update() throws Exception {
        facadeWorld.update();
        facadeCombat.update();
        facadeRender.render(facadeWorld.getPlayer(), RessourceManager.getInstance().getRoom());
        facadeInteraction.update();
        facadeQuest.update();
        if(facadeRender.isPause()){
            pauseMenuUI.render();
        }
    }

    public void dispose(){
        facadeRender.dispose();
    }

    @Override
    public void displayEvent(GameEvent event, String message) throws Exception {
        if(event == GameEvent.PLAYER_LOST){
            facadeRender.setGameOver(true);
        }else if(event == GameEvent.PLAYER_REVIVE){
            facadeRender.setGameOver(false);
        }else if(event == GameEvent.DIALOG_STARTED){
            facadeRender.setDialog(true);
        }else if(event == GameEvent.DIALOG_FINISHED){
            facadeRender.setDialog(false);
        }else if(event == GameEvent.SHOP_OPENED) {
            MerchandPNJ marchand = null;
            for(PNJ pnj : RessourceManager.getInstance().getRoom().getPNJs()) {
                if(pnj instanceof MerchandPNJ) {
                    marchand = (MerchandPNJ) pnj;
                    break;
                }
            }
            if(marchand != null) {
                facadeRender.openShop(marchand, RessourceManager.getInstance().getPlayer());
            }
        }else if(event == GameEvent.SHOP_CLOSED) {
            facadeRender.closeShop();
        }else if (event == GameEvent.PAUSE_STARTED){
            pauseMenuUI.show();
            facadeRender.setPause(true);
        }else if (event == GameEvent.PAUSE_FINISHED){
            facadeRender.setPause(false);
        }
    }

    public SaveData buildSaveData() throws Exception {
        SaveData saveData = new SaveData();
        Player player = facadeWorld.getPlayer();
        saveData.playerX = player.getX();
        saveData.playerY = player.getY();
        saveData.playerLife = player.getLife();
        saveData.currentRoom = facadeWorld.getRoom().getName();

        List<String[]> activeQuests = new ArrayList<>();
        for(Quest quest : facadeQuest.getActiveQuests()){
            activeQuests.add(quest.toSaveArray());
        }
        saveData.activeQuests = activeQuests;
        Map<String, Integer> inventoryMap = new HashMap<>();
        for(Pickable item : player.getInventory().getItems()){
            String itemName = item.getName();
            inventoryMap.put(itemName, inventoryMap.getOrDefault(itemName, 0) + 1);
        }
        saveData.inventory = inventoryMap;
        saveData.gold = player.getInventory().getGold();
        List<String> deadEnemies = new ArrayList<>();
        Map<String, Integer> merchandStockMap = new HashMap<>();
        for(Room room : RessourceManager.getInstance().getRooms()){
            for (Enemy enemy : room.getEnemys()){
                if(!enemy.isAlive()){
                    deadEnemies.add(room.getName() + "-" + enemy.getName());
                }
            }
            for(PNJ pnj : room.getPNJs()){
                if(pnj instanceof MerchandPNJ) {
                    for(Pickable item : ((MerchandPNJ) pnj).getStock()){
                        String itemName = item.getName();
                        merchandStockMap.put(itemName, merchandStockMap.getOrDefault(itemName, 0) + 1);
                    }
                }
            }
        }
        saveData.deadEnemies = deadEnemies;
        saveData.merchandStock = merchandStockMap;
        return saveData;
    }

    public void applySaveData(SaveData saveData) throws Exception {
        facadeWorld.getPlayer().setLife(saveData.playerLife);
        facadeWorld.getPlayer().teleport(saveData.playerX, saveData.playerY);
        for(Room room : RessourceManager.getInstance().getRooms()){
            if(room.getName().equals(saveData.currentRoom)){
                RessourceManager.getInstance().setRoom(room);
                break;
            }
        }
        for(Room room : RessourceManager.getInstance().getRooms()) {
            for(Enemy enemy : room.getEnemys()) {
                String key = room.getName() + "-" + enemy.getName();
                if(saveData.deadEnemies.contains(key)) {
                    enemy.kill();
                }
            }
            for(PNJ pnj : room.getPNJs()) {
                if(pnj instanceof MerchandPNJ) {
                    for(Map.Entry<String, Integer> entry : saveData.merchandStock.entrySet()) {
                        int stockItem = ((MerchandPNJ) pnj).countStock(entry.getKey());
                        while(stockItem > entry.getValue()) {
                           Pickable removeItem = ((MerchandPNJ) pnj).getItemFromStock(entry.getKey());
                           ((MerchandPNJ) pnj).removeFromStock(removeItem);
                           stockItem -= 1;
                        }
                    }
                }
            }
        }
        for(String[] questData : saveData.activeQuests) {
            Quest quest = QuestFactory.createQuest(questData);
            QuestManager.getInstance().addQuest(quest);
        }
        Player player = facadeWorld.getPlayer();
        for(Map.Entry<String, Integer> entry : saveData.inventory.entrySet()) {
            for(int i = 0; i < entry.getValue(); i++) {
                player.getInventory().addItem(ItemFactory.createItem(entry.getKey(), 0, 0));
            }
        }
        player.getInventory().addGold(saveData.gold);
    }
}
