package com.ProgPro.State;

public class ShopState extends State {


    public ShopState(Stateful stateful) {
        super(stateful);
    }

    @Override
    public void setAliveState() throws Exception {
       stateful.setState(new AliveState(stateful));
    }

    @Override
    public void setDeadState() throws Exception {
        throw new Exception("Impossible to switch from shop to death");
    }

    @Override
    public void setDialogState() throws Exception {
        throw new Exception("Impossible to switch from shop to dialog");
    }

    @Override
    public void setShopState() throws Exception {
        throw new Exception("State of " + stateful + " is already ShopState");
    }
}
