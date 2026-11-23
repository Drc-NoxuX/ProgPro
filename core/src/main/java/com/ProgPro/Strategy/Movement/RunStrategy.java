package com.ProgPro.Strategy.Movement;

import com.badlogic.gdx.physics.box2d.Body;

public class RunStrategy extends AbstractMovementStrategy {
    @Override
    protected float getSpeed() {
        return 10f;
    }

    @Override
    protected void applyVertical(Body body) {

    }
}
