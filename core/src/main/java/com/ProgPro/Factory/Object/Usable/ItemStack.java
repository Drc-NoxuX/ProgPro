package com.ProgPro.Factory.Object.Usable;

import com.ProgPro.Factory.Object.BasicObject;

public class ItemStack {
    BasicObject item;
    int amount;

    public ItemStack(BasicObject item, int amount) {
        this.item = item;
    }
}
