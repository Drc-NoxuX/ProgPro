package com.ProgPro.Strategy.CombatEnemy;

import com.ProgPro.Factory.Object.Enemies.CombatAction;
import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.Player;

public class PrudentStrategy implements EnemyCombatStrategy{
    @Override
    public CombatAction decide(Player player, Enemy enemy) {
        return CombatAction.BACK;
    }
}
