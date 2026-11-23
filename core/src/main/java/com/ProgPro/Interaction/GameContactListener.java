package com.ProgPro.Interaction;

import com.ProgPro.Facade.FacadeCombat;
import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.Player;
import com.badlogic.gdx.physics.box2d.*;

public class GameContactListener implements ContactListener {

    FacadeCombat facadeCombat;

    public GameContactListener(FacadeCombat facadeCombat) {
        this.facadeCombat = facadeCombat;
    }

    @Override
    public void beginContact(Contact contact) {
        Object dataA = contact.getFixtureA().getBody().getUserData();
        Object dataB = contact.getFixtureB().getBody().getUserData();
        if (dataA instanceof Player) {
            Player player = (Player) dataA;
            if("ground".equals(dataB)){
                player.setOnGround(true);
            }else{
                if(dataB instanceof Enemy){
                    try {
                        facadeCombat.startCombat((Enemy) dataB);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }else if(dataB instanceof Player){
            Player player = (Player) dataB;
            if("ground".equals(dataA)){
                player.setOnGround(true);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object dataA = contact.getFixtureA().getBody().getUserData();
        Object dataB = contact.getFixtureB().getBody().getUserData();
        if (dataA instanceof Player) {
            Player player = (Player) dataA;
            if("ground".equals(dataB)){
                player.setOnGround(false);
            }

        }else if(dataB instanceof Player){
            Player player = (Player) dataB;
            if("ground".equals(dataA)){
                player.setOnGround(false);
            }
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
