package com.ProgPro.Factory.Object.PNJ;

import com.ProgPro.Dialog;
import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Player;

public abstract class PNJ extends BasicObject {

    private Dialog dialog;

    public PNJ(int x, int y, String name) {
        super(x, y, name);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    protected Dialog getDialog() {
        return dialog;
    }

    public abstract void interact(Player player) throws Exception;
}
