package com.ProgPro.Factory.Object.Usable;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Potion extends BasicObject implements Item{

    private int amountOfHealthGiven;

    public Potion(int x, int y, String name, int amountOfHealthGiven) {
        super(x, y, name);
        this.amountOfHealthGiven = amountOfHealthGiven;
    }

    @Override
    public void use(Player player) {
        player.heal(amountOfHealthGiven);
    }

    @Override
    public void onPickup(Player player) throws Exception {
        player.getInventory().addItem(this);
        GameEventBus.getInstance().publish(GameEvent.ITEM_PICKED, "Objet ramassé !");
    }

    public int getAmountOfHealthGiven() {
        return amountOfHealthGiven;
    }

    public void setAmountOfHealthGiven(int amountOfHealthGiven) {
        this.amountOfHealthGiven = amountOfHealthGiven;
    }

    public boolean isSmall() {
        return amountOfHealthGiven == 3;
    }
}
