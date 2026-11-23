package com.ProgPro.Factory.Object.Usable;

import com.ProgPro.Factory.Object.Player;

public interface Pickable {
    void onPickup(Player player) throws Exception;
    String getName();
}
