package com.ProgPro.Factory.Object.PNJ;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Object.Usable.Pickable;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.State.ShopState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MerchandPNJ extends PNJ{

    private List<Pickable> stock;
    private Map<Pickable, Integer> prices;

    public MerchandPNJ(int x, int y, String name) {
        super(x, y, name);
        stock = new ArrayList<>();
        prices = new HashMap<>();
    }

    @Override
    public void interact(Player player) throws Exception {
        player.setState(new ShopState(player));
        player.setIsInShop(true);
        GameEventBus.getInstance().publish(GameEvent.SHOP_OPENED, "");
    }

    public void addToStock(Pickable pickable, int price){
        stock.add(pickable);
        prices.put(pickable, price);
    }

    public List<Pickable> getStock(){
        return stock;
    }

    public int getPrice(Pickable pickable){
        return prices.get(pickable);
    }

    public void removeFromStock(Pickable item) {
        stock.remove(item);
        prices.remove(item);
    }

    public int countStock(String name) {
        int count = 0;
        for(Pickable item : stock) {
            if(item.getName().equals(name)) {
                count++;
            }
        }
        return count;
    }

    public Pickable getItemFromStock(String name) {
        for(Pickable item : stock) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
