package com.ProgPro.Factory.Object.Usable;

import com.ProgPro.Factory.Object.BasicObject;

public class ItemFactory {

    public static Pickable createItem(String name, int x, int y){
        return switch (name) {
            case "Gold" -> new Gold(x, y, "Gold");
            case "Potion" -> new Potion(x, y, "Potion", 3);
            case "Grande Potion" -> new Potion(x, y, "Grande Potion", 10);
            default -> throw new IllegalArgumentException("Invalid item type");
        };
    }
}
