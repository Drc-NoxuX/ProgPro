package com.ProgPro.Strategy.Movement;

import com.badlogic.gdx.physics.box2d.Body;

public class WalkStrategy extends AbstractMovementStrategy {
    @Override
    protected float getSpeed() {
        return 5f;
    }

    @Override
    protected void applyVertical(Body body) {

    }
}
