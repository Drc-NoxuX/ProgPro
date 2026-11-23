package com.ProgPro.Managers;

import com.ProgPro.Factory.Object.Room.Room;
import com.ProgPro.Factory.Object.Usable.ItemFactory;

import java.util.List;

public class RoomItemsHandler {

    private List<Room> mapRooms;

    public RoomItemsHandler(List<Room> mapRooms)
    {
        this.mapRooms = mapRooms;
    }

    public void setUpItems(){
        mapRooms.get(2).addItem(ItemFactory.createItem("Gold", 20, 228));
        mapRooms.get(2).addItem(ItemFactory.createItem("Gold", 40, 228));
        mapRooms.get(3).addItem(ItemFactory.createItem("Gold", 50, 228));
        mapRooms.get(3).addItem(ItemFactory.createItem("Gold", 30, 228));
        mapRooms.get(3).addItem(ItemFactory.createItem("Potion", 80, 228));
        mapRooms.get(5).addItem(ItemFactory.createItem("Gold", 20, 228));
        mapRooms.get(5).addItem(ItemFactory.createItem("Potion", 40, 228));
        mapRooms.get(5).addItem(ItemFactory.createItem("Potion", 50, 228));
        mapRooms.get(7).addItem(ItemFactory.createItem("Gold", 20, 228));
    }
}
