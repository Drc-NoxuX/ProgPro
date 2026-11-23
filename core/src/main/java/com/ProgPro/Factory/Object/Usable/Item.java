package com.ProgPro.Factory.Object.Usable;

import com.ProgPro.Factory.Object.Player;

public interface Item extends Pickable {
    void use(Player player);
}
