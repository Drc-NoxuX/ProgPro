package com.ProgPro.State;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Player;

import java.util.Scanner;

public class DeathState extends State{


    public DeathState(Stateful stateful) {
        super(stateful);
    }

    @Override
    public void setAliveState() throws Exception {
        stateful.setState(new AliveState(stateful));
        stateful.revive();
    }

    @Override
    public void setDeadState() throws Exception {
        throw new Exception("State of " + stateful + " is already DeadState");
    }

    @Override
    public void setDialogState() throws Exception {
        throw new Exception("Impossible to switch from death to dialog");
    }

    @Override
    public void setShopState() throws Exception {
        throw new Exception("Impossible to switch from death to shop");
    }
}
