package com.ProgPro.Factory.Object.Room;

import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.PNJ.PNJ;
import com.ProgPro.Factory.Object.Usable.Pickable;

import java.util.ArrayList;
import java.util.List;

public abstract class Room {
    private String name;
    private final String path;
    private Room leftRoom;
    private Room rightRoom;
    private Room upRoom;
    private Room downRoom;
    private List<Pickable> items;

    public Room(String name, String path) {
        this.name = name;
        this.path = path;
        items = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Room getLeftRoom() {
        return leftRoom;
    }
    public void setLeftRoom(Room leftRoom) {
        this.leftRoom = leftRoom;
    }
    public Room getRightRoom() {
        return rightRoom;
    }
    public void setRightRoom(Room rightRoom) {
        this.rightRoom = rightRoom;
    }
    public Room getUpRoom() {
        return upRoom;
    }
    public void setUpRoom(Room upRoom) {
        this.upRoom = upRoom;
    }
    public Room getDownRoom() {
        return downRoom;
    }
    public void setDownRoom(Room downRoom) {
        this.downRoom = downRoom;
    }
    public List<Pickable> getItems() {
        return items;
    }
    public void addItem(Pickable item) {
        items.add(item);
    }
    public String getPath() {
        return path;
    }

    public abstract List<PNJ> getPNJs();
    public abstract List<Enemy> getEnemys();

}
