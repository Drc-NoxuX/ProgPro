package com.ProgPro;

import com.ProgPro.Factory.Object.Usable.Pickable;
import com.ProgPro.Factory.Object.Usable.Potion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Inventory {
    private List<Pickable> items;
    private int gold;

    public Inventory() {
        items = new ArrayList<>();
        gold = 0;
    }

    public List<Pickable> getItems() {
        return items;
    }
    public void addItem(Pickable item){
        items.add(item);
    }
    public void removeItem(Pickable item){
        items.remove(item);
    }

    public void addGold(int amount){
        gold += amount;
   }
    public void removeGold(int amount){
        gold -= amount;
   }

    public int getGold() {
        return gold;
    }

    public boolean combine(){
        int potionCount = 0;
        for(Pickable item : items){
            if(item instanceof Potion && ((Potion)item).isSmall()){
                potionCount++;
            }
        }

        if(potionCount < 2) return false;
        Iterator<Pickable> it = items.iterator();
        int count = 0;
        while(it.hasNext() && count < 2){
            Pickable item = it.next();
            if(item instanceof Potion && ((Potion)item).isSmall()) {
                it.remove();
                count++;
            }
        }
        try{
            addItem(new Potion(0,0, "Grande Potion", 5));
            return true;
        }catch(Exception e){
           return false;
        }
    }
}
