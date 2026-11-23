package com.ProgPro.Strategy.CombatEnemy;

import com.ProgPro.Factory.Object.Enemies.CombatAction;
import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.Player;

public interface EnemyCombatStrategy {
    CombatAction decide(Player player, Enemy enemy);
}
