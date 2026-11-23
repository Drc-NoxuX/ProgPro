package com.ProgPro.Factory.Object.Enemies;

import com.ProgPro.Factory.Attack.Attack;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Strategy.CombatEnemy.PrudentStrategy;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CloseCombatEnemy extends Enemy {

    public CloseCombatEnemy(int x, int y, String name) {
        super(x, y, name);
        setCurrentAttack(new Attack("attack1", 100, 8));
        setLife(8);
    }

    @Override
    public CombatAction decideTurn(Player player) {
        if(getLife() <= 2){
            setEnemyCombatStrategy(new PrudentStrategy());
            return getEnemyCombatStrategy().decide(player, this);
        }
        int range_threshold = 100;
        if(getDistance(player) <= range_threshold) {
            return CombatAction.ATTACK;
        }else {
            return CombatAction.FORWARD;
        }
    }

    @Override
    public int getCombatDistance(){
        return 15;
    }
}
