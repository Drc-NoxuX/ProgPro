package com.ProgPro.Managers;

import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Object.Room.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;

public class RessourceManager {

    private static RessourceManager instance;
    private final Player player;
    private World world;
    private Room currentRoom;
    private List<Room> mapRooms;

    private RessourceManager() throws Exception {
        world = new World(new Vector2(0, -20f), true);
        player = new Player(100,200, "Player");
        player.createBody(world,32,48);
        mapRooms = createRooms();
        setConnectionBetweenRooms();
        populateRooms();
        currentRoom = mapRooms.get(0);
    }
    public static RessourceManager getInstance() throws Exception {
        if (instance == null) {
            instance = new RessourceManager();
        }
        return instance;
    }

    private List<Room> createRooms() {
        mapRooms = new ArrayList<>();
        mapRooms.add(RoomFactory.createRoom("Pacific", "Room1", Gdx.files.internal("Rooms/Room1.tmx").path()));
        mapRooms.add(RoomFactory.createRoom("Combat", "Room2", Gdx.files.internal("Rooms/Room2.tmx").path()));
        mapRooms.add(RoomFactory.createRoom("Combat", "Room3", Gdx.files.internal("Rooms/Room3.tmx").path()));
        mapRooms.add(RoomFactory.createRoom("Pacific", "Room4", Gdx.files.internal("Rooms/Room4.tmx").path()));
        mapRooms.add(RoomFactory.createRoom("Pacific", "Room5", Gdx.files.internal("Rooms/Room5.tmx").path()));
        mapRooms.add(RoomFactory.createRoom("Pacific", "Room6", Gdx.files.internal("Rooms/Room6.tmx").path()));
        mapRooms.add(RoomFactory.createRoom("Pacific", "Room7", Gdx.files.internal("Rooms/Room7.tmx").path()));
        mapRooms.add(RoomFactory.createRoom("Combat", "Room8", Gdx.files.internal("Rooms/Room8.tmx").path()));
        mapRooms.add(RoomFactory.createRoom("Combat", "Room9", Gdx.files.internal("Rooms/Room9.tmx").path()));
        mapRooms.add(RoomFactory.createRoom("Combat", "Room10", Gdx.files.internal("Rooms/Room10.tmx").path()));

        return mapRooms;
    }

    private void populateRooms() throws Exception {
        RoomPopulationHandler handler = new RoomPopulationHandler(world, mapRooms);
        handler.populate();
        RoomItemsHandler itemsHandler = new RoomItemsHandler(mapRooms);
        itemsHandler.setUpItems();
        initEnemyBody();
    }

    private void setConnectionBetweenRooms(){
        mapRooms.getFirst().setRightRoom(mapRooms.get(1));
        mapRooms.get(1).setLeftRoom(mapRooms.getFirst());
        mapRooms.get(1).setRightRoom(mapRooms.get(2));
        mapRooms.get(2).setLeftRoom(mapRooms.get(1));
        mapRooms.get(2).setRightRoom(mapRooms.get(3));
        mapRooms.get(3).setLeftRoom(mapRooms.get(2));
        mapRooms.get(3).setRightRoom(mapRooms.get(4));
        mapRooms.get(4).setLeftRoom(mapRooms.get(3));
        mapRooms.get(4).setRightRoom(mapRooms.get(5));
        mapRooms.get(5).setLeftRoom(mapRooms.get(4));
        mapRooms.get(5).setRightRoom(mapRooms.get(6));
        mapRooms.get(6).setLeftRoom(mapRooms.get(5));
        mapRooms.get(6).setDownRoom(mapRooms.get(7));
        mapRooms.get(7).setUpRoom(mapRooms.get(6));
        mapRooms.get(7).setLeftRoom(mapRooms.get(9));
        mapRooms.get(9).setRightRoom(mapRooms.get(7));
        mapRooms.get(7).setRightRoom(mapRooms.get(8));
        mapRooms.get(8).setLeftRoom(mapRooms.get(7));
    }

    public void initEnemyBody(){
        for(Room room : mapRooms){
            for(Enemy enemy : room.getEnemys()){
                enemy.getBody().setActive(false);
            }
        }
    }

    public void activateRoom(Room room) {
        for(Enemy enemy : room.getEnemys()) {
            enemy.getBody().setActive(true);
        }
    }

    public void deactivateRoom(Room room) {
        for(Enemy enemy : room.getEnemys()) {
            enemy.getBody().setActive(false);
        }
    }

    public Player getPlayer() {
        return player;
    }
    public World getWorld() {
        return world;
    }

    public Room getRoom() {
        return currentRoom;
    }
    public void setRoom(Room room) {
        this.currentRoom = room;
    }

    public List<Room> getRooms(){
        return mapRooms;
    }

    public static void reset(){
        instance = null;
    }
}
