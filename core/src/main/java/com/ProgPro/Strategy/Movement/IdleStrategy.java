package com.ProgPro.Strategy.Movement;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Player;
import com.badlogic.gdx.physics.box2d.Body;

public class IdleStrategy extends AbstractMovementStrategy{

    @Override
    protected float getSpeed() {
        return 0f;
    }

    @Override
    protected void applyVertical(Body body) {

    }
}
