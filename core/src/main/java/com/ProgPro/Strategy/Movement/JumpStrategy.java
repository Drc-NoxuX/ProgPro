package com.ProgPro.Strategy.Movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class JumpStrategy extends AbstractMovementStrategy {

    private static final float JUMP_FORCE = 12f; //force de l'impulsion du saut en Newtons dans Box2D
    private boolean jumpApplied = false;

    @Override
    protected float getSpeed() {
        return 4f;
    }

    @Override
    protected void applyVertical(Body body){
        if(!jumpApplied){
            body.applyLinearImpulse(new Vector2(0, JUMP_FORCE), body.getWorldCenter(), true);//On applique la force au centre du corps
            jumpApplied = true;
        }
    }

    @Override
    public boolean canTransitionTo(MovementStrategy movementStrategy) {
        return !(movementStrategy instanceof JumpStrategy);
    }

    public void reset(){
        jumpApplied = false;
    }
}
