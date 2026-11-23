package com.ProgPro.Strategy.Movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class FallStrategy extends AbstractMovementStrategy {

    private static final float FALL_MULTIPLIER = 1.5f; //Multiplie la gravité pendant la chute

    @Override
    protected float getSpeed() {
        return 4f;
    }

    @Override
    protected void applyVertical(Body body) {
        if(body.getLinearVelocity().y < 0){//On tombe vraiment
            body.applyForceToCenter(new Vector2(0, -9.8f*FALL_MULTIPLIER), true); //gravité terrestre
        }
    }
}
