package com.ProgPro.Strategy.Movement;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.PhysicsObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class AbstractMovementStrategy implements MovementStrategy {

    protected abstract float getSpeed();

    @Override
    public void setStrategy(BasicObject receiver){
        if(!(receiver instanceof PhysicsObject)){
            return;
        }
        Body body = ((PhysicsObject)receiver).getBody();
        if(body == null){
            return;
        }
        Vector2 vel = body.getLinearVelocity(); //Récupère la vitesse du corps (vel.x : vitesse horizontale; vel.y : vitesse verticale (avec gravité))
        if(receiver instanceof Movable m){
            body.setLinearVelocity(m.getHorizontalDirection()*getSpeed(), vel.y);
        }

        applyVertical(body);
    }

    protected  abstract void applyVertical(Body body);

    @Override
    public boolean canTransitionTo(MovementStrategy movementStrategy){
        return true;
    }
}
