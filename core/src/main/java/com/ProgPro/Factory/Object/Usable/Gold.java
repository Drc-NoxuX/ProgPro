package com.ProgPro.Factory.Object.Usable;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Gold extends BasicObject implements Pickable {

    private int value;

    public Gold(int x, int y, String name) {
        super(x, y, name);
        value = 5;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void onPickup(Player player) throws Exception {
        player.getInventory().addGold(value);
        GameEventBus.getInstance().publish(GameEvent.ITEM_PICKED, "Objet ramassé !");
    }
}
