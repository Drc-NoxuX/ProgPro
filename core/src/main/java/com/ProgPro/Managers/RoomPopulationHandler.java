package com.ProgPro.Managers;

import com.ProgPro.Dialog;
import com.ProgPro.Factory.Object.Enemies.EnemyFactory;
import com.ProgPro.Factory.Object.PNJ.BasicPNJ;
import com.ProgPro.Factory.Object.PNJ.MerchandPNJ;
import com.ProgPro.Factory.Object.PNJ.PNJFactory;
import com.ProgPro.Factory.Object.PNJ.QuestPNJ;
import com.ProgPro.Factory.Object.Room.CombatRoom;
import com.ProgPro.Factory.Object.Room.PacificRoom;
import com.ProgPro.Factory.Object.Room.Room;
import com.ProgPro.Factory.Object.Usable.Potion;
import com.ProgPro.Factory.Quest.Quest;
import com.ProgPro.Factory.Quest.QuestFactory;
import com.ProgPro.Factory.Quest.SlayAllEnemiesQuest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.bladecoder.ink.runtime.Story;

import java.util.List;

public class RoomPopulationHandler {

    private World world;
    private List<Room> mapRooms;

    public RoomPopulationHandler(World world, List<Room> mapRooms) {
        this.mapRooms = mapRooms;
        this.world = world;
    }

    private void setUpPierrot() throws Exception {
        ((PacificRoom)mapRooms.getFirst()).addPNJ(PNJFactory.createPNJ("basic", "Pierrot", 400, 228));
        Story story = new Story(Gdx.files.internal("Dialogs/PierrotDialog.json").readString());
        Dialog dialog = new Dialog(story);
        mapRooms.getFirst().getPNJs().getFirst().setDialog(dialog);
    }

    private void setUpChef() throws Exception {
        ((PacificRoom)mapRooms.get(4)).addPNJ(PNJFactory.createPNJ("quest", "Chef", 500, 228));
        Quest quest = QuestFactory.createQuest(new String[]{"slayall", "Carnage", "Tuez tous les ennemis de la grotte"});
        QuestPNJ chef = (QuestPNJ) mapRooms.get(4).getPNJs().getFirst();
        chef.setQuest(quest);
        ((SlayAllEnemiesQuest)quest).addRoom(mapRooms.get(7));
        ((SlayAllEnemiesQuest)quest).addRoom(mapRooms.get(8));
        ((SlayAllEnemiesQuest)quest).addRoom(mapRooms.get(9));
        Story firstQuest = new Story(Gdx.files.internal("Dialogs/FirstQuest.json").readString());
        Dialog firstQuestDialog = new Dialog(firstQuest);
        chef.setDialog(firstQuestDialog);
    }

    private void setUpRoublard(){
        ((PacificRoom)mapRooms.get(3)).addPNJ(PNJFactory.createPNJ("merchand", "Le Roublard", 250, 228));
        MerchandPNJ marchand = (MerchandPNJ) ((PacificRoom)mapRooms.get(3)).getPNJs().getFirst();
        marchand.addToStock(new Potion(0, 0, "Potion", 3), 5);
        marchand.addToStock(new Potion(0, 0, "Potion", 3), 5);
        marchand.addToStock(new Potion(0, 0, "Potion", 3), 5);
        marchand.addToStock(new Potion(0, 0, "Grande Potion", 10), 8);
    }

    private void setUpGuide() throws Exception {
        Story story = new Story(Gdx.files.internal("Dialogs/GuideDialog.json").readString());
        Dialog dialog = new Dialog(story);
        ((PacificRoom)mapRooms.get(5)).addPNJ(PNJFactory.createPNJ("basic", "Le Guide", 250, 228));
        BasicPNJ guide = (BasicPNJ) mapRooms.get(5).getPNJs().getFirst();
        guide.setDialog(dialog);
    }

    private void setUpEnnemis() throws Exception {
        ((CombatRoom)mapRooms.get(1)).addEnemy(EnemyFactory.create("close", 150, 228), world);
        ((CombatRoom)mapRooms.get(1)).addEnemy(EnemyFactory.create("far", 400,228), world);
        ((CombatRoom)mapRooms.get(2)).addEnemy(EnemyFactory.create("close", 300, 228), world);
        ((CombatRoom)mapRooms.get(7)).addEnemy(EnemyFactory.create("close", 450, 228), world);
        ((CombatRoom)mapRooms.get(7)).addEnemy(EnemyFactory.create("far", 300,228), world);
        ((CombatRoom)mapRooms.get(8)).addEnemy(EnemyFactory.create("close", 350, 228), world);
        ((CombatRoom)mapRooms.get(8)).addEnemy(EnemyFactory.create("far", 600,228), world);
        ((CombatRoom)mapRooms.get(8)).addEnemy(EnemyFactory.create("far", 450,228), world);
        ((CombatRoom)mapRooms.get(9)).addEnemy(EnemyFactory.create("far", 500,228), world);
        ((CombatRoom)mapRooms.get(9)).addEnemy(EnemyFactory.create("close", 350,228), world);
    }

    public void populate() throws Exception {
        setUpPierrot();
        setUpChef();
        setUpRoublard();
        setUpGuide();
        setUpEnnemis();
    }

}
