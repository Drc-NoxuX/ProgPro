package com.ProgPro.Strategy.Combat;

import com.ProgPro.Factory.Attack.Attack;
import com.ProgPro.Factory.Object.Enemies.CombatAction;
import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Object.Usable.Pickable;
import com.ProgPro.Factory.Object.Usable.Potion;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;

import java.util.Iterator;

public class TurnBasedCombat implements CombatStrategy {

    private Player player;
    private Enemy enemy;
    private Attack currentAttack;
    private CombatTurn currentTurn = CombatTurn.PLAYER_TURN;
    private boolean combatEnded = false;
    private float timer = 0f;
    private static final float TURN_DELAY = 1.5f;

    public TurnBasedCombat(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public void setCurrentAttack(Attack attack) {
        this.currentAttack = attack;
    }

    @Override
    public void executeTurn(float deltaTime) throws Exception {
        switch (currentTurn) {
            case PLAYER_TURN_STARTING:
                timer += deltaTime;
                if(timer >= TURN_DELAY) {
                    GameEventBus.getInstance().publish(GameEvent.PLAYER_TURN, "C'est votre tour !");
                    timer = 0f;
                    currentTurn = CombatTurn.PLAYER_TURN;
                }
                break;
            case PLAYER_TURN:
                handlePlayerTurn();
                if(!enemy.isAlive()){
                    currentTurn = CombatTurn.COMBAT_ENDING;
                }
                break;
            case ENEMY_TURN:
                timer += deltaTime;
                if(timer >= TURN_DELAY) {
                    handleEnemyTurn();
                    timer = 0f;
                    if(!player.isAlive()){
                        currentTurn = CombatTurn.COMBAT_ENDING;
                    }
                }
                break;
            case COMBAT_ENDING:
                timer += deltaTime;
                if(timer >= TURN_DELAY) {
                    currentTurn = CombatTurn.COMBAT_END;
                }
                break;
            case COMBAT_END:
                if(!combatEnded){
                    combatEnded = true;
                    if(!enemy.isAlive()){
                        GameEventBus.getInstance().publish(GameEvent.PLAYER_WON, "Fin du combat");
                    }else{
                        GameEventBus.getInstance().publish(GameEvent.PLAYER_LOST, "Fin du combat");
                    }
                    break;
                }
        }
    }

    private void handlePlayerTurn() throws Exception {
        if(currentAttack != null){
            if(enemy.getDistance(player) <= currentAttack.getRange()){
                enemy.takeDamage(currentAttack.getName(), currentAttack.getAmountOfDamage());
            }else{
                GameEventBus.getInstance().publish(GameEvent.ATTACK_MISSED, "L'ennemi est hors de portée, attaque manquée");
            }
            currentAttack = null;
            currentTurn = CombatTurn.ENEMY_TURN;
        }
    }

    private void handleEnemyTurn() throws Exception {
        CombatAction ca = enemy.decideTurn(player);
        if(ca == CombatAction.ATTACK){
            if(enemy.getDistance(player) <= enemy.getAttack().getRange()){
                player.takeDamage(enemy.getName(), enemy.getAttack().getName(), enemy.getAttack().getAmountOfDamage());
            }else{
                GameEventBus.getInstance().publish(GameEvent.ATTACK_MISSED, "Vous êtes hors de portée, attaque manquée");
            }
        }else if (ca == CombatAction.BACK){
            enemy.moveBack(player);
            GameEventBus.getInstance().publish(GameEvent.ENEMY_MOVED,"L'ennemi recule");
        }else{
            enemy.moveForward(player);
            GameEventBus.getInstance().publish(GameEvent.ENEMY_MOVED, "L'ennemi avance");
        }
        currentTurn = CombatTurn.PLAYER_TURN_STARTING;
    }

    public void usePotion() throws Exception {
        Iterator<Pickable> it = player.getInventory().getItems().iterator();
        while(it.hasNext()) {
            Pickable item = it.next();
            if(item instanceof Potion) {
                ((Potion) item).use(player);
                it.remove();
                currentTurn = CombatTurn.ENEMY_TURN;
                GameEventBus.getInstance().publish(GameEvent.ITEM_PICKED, "Vous utilisez une potion !");
                break;
            }
        }
    }
}


