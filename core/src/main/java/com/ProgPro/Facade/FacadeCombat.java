package com.ProgPro.Facade;

import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.GameEvent.GameEventObserver;
import com.ProgPro.Managers.RessourceManager;
import com.ProgPro.Strategy.Combat.TurnBasedCombat;
import com.ProgPro.UI.CombatUI;
import com.badlogic.gdx.Gdx;

public class FacadeCombat implements GameEventObserver {
    private Player player;
    private Enemy enemy;
    private CombatUI combatUI;
    private TurnBasedCombat turnBasedCombat;
    private boolean shouldStartCombat = false;
    private float enemyOriginalX;
    private float enemyOriginalY;
    private float playerOriginalX;
    private float playerOriginalY;

    private static FacadeCombat instance;

    public FacadeCombat() throws Exception {
        player = RessourceManager.getInstance().getPlayer();
        GameEventBus.getInstance().subscribe(this);
    }

    public Enemy getEnemy(){
        return enemy;
    }

    public void setCombatUI(CombatUI combatUI){
        this.combatUI = combatUI;
    }

    public void update() throws Exception {
        if(shouldStartCombat){
            enemyOriginalX = enemy.getX();
            enemyOriginalY = enemy.getY();
            playerOriginalX = player.getX();
            playerOriginalY = player.getY();
            turnBasedCombat = new TurnBasedCombat(player, enemy);
            combatUI.setTurnBasedCombat(turnBasedCombat);
            enemy.setInCombat(true);
            player.setIsCombat(true);
            GameEventBus.getInstance().publish(GameEvent.COMBAT_STARTED, "Le combat commence !");
            player.getBody().setTransform(10, 228/32f, 0);
            enemy.getBody().setTransform(enemy.getCombatDistance(), 228/32f, 0);
            player.getBody().setLinearVelocity(0, 0);
            enemy.getBody().setLinearVelocity(0, 0);
            shouldStartCombat = false;
        }else {
            if(player.isInCombat() && turnBasedCombat != null) {
                turnBasedCombat.executeTurn(Gdx.graphics.getDeltaTime());
            }
        }
    }

    public void startCombat(Enemy enemy) throws Exception {
        if(!player.isInCombat() && !shouldStartCombat){
            this.enemy = enemy;
            shouldStartCombat = true;
        }
    }


    @Override
    public void displayEvent(GameEvent event, String message) throws Exception {
        if(event == GameEvent.PLAYER_WON){
            player.setIsCombat(false);
            enemy.setInCombat(false);
            enemy.getBody().getWorld().destroyBody(enemy.getBody());
            player.getBody().setTransform(playerOriginalX/32f, playerOriginalY/32f, 0);
        }else if(event == GameEvent.PLAYER_LOST){
            player.setIsCombat(false);
            enemy.setInCombat(false);
            enemy.getBody().setTransform(enemyOriginalX/32f, enemyOriginalY/32f, 0);
        } else if(event == GameEvent.PLAYER_REVIVE){
            player.revive();
            enemy.setInCombat(false);
            player.getBody().setTransform(playerOriginalX/32f - 2, playerOriginalY/32f, 0);
            player.setIsCombat(false);
        }
    }
}
