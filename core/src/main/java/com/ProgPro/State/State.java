package com.ProgPro.State;

//La classe qui donne les transitions possibles entre les états
public abstract class State {
    protected Stateful stateful;

    public State(Stateful stateful) {
        this.stateful = stateful;
    }

    public abstract void setAliveState () throws Exception;
    public abstract void setDeadState () throws Exception;
    public abstract void setDialogState () throws Exception;
    public abstract void setShopState () throws Exception;
}
