package com.ProgPro.Factory.Object.Enemies;

import com.ProgPro.Factory.Attack.Attack;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Strategy.CombatEnemy.PrudentStrategy;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FarCombatEnemy extends Enemy{

    public FarCombatEnemy(int x, int y, String name) {
        super(x, y, name);
        setCurrentAttack(new Attack("attack1", 200, 3));
        setLife(5);
    }

    @Override
    public CombatAction decideTurn(Player player) {
        if(getLife() <= 2){
            setEnemyCombatStrategy(new PrudentStrategy());
            return getEnemyCombatStrategy().decide(player, this);
        }
        int range_threshold = 100;
        int max_threshold = 200;
        if(getDistance(player) < range_threshold) {
            return CombatAction.BACK;
        }else if (getDistance(player) > max_threshold) {
            return CombatAction.FORWARD;
        }else{
            return CombatAction.ATTACK;
        }
    }
}
