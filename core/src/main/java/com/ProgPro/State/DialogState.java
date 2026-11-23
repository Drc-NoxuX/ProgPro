package com.ProgPro.State;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Player;

public class DialogState extends State {


    public DialogState(Stateful stateful) {
        super(stateful);
    }

    @Override
    public void setAliveState() throws Exception {
       stateful.setState(new AliveState(stateful));
    }

    @Override
    public void setDeadState() throws Exception {
        throw new Exception("Impossible to switch from dialog to death");
    }

    @Override
    public void setDialogState() throws Exception {
        throw new Exception("State of " + stateful + " is already DialogState");
    }

    @Override
    public void setShopState() throws Exception {
        stateful.setState(new ShopState(stateful));
    }
}
