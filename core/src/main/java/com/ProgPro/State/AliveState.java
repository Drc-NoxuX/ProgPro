package com.ProgPro.State;

import com.ProgPro.Factory.Object.Player;

public class AliveState extends State {


    public AliveState(Stateful stateful) {
        super(stateful);
    }

    @Override
    public void setAliveState() throws Exception {
        throw new Exception("State of " + stateful + " is already AliveState");
    }

    @Override
    public void setDeadState() throws Exception {
        stateful.setState(new DeathState(stateful));
    }

    @Override
    public void setDialogState() throws Exception {
       stateful.setState(new DialogState(stateful));
    }

    @Override
    public void setShopState() throws Exception {
        stateful.setState(new ShopState(stateful));
    }
}
