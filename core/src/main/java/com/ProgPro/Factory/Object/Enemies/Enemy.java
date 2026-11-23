package com.ProgPro.Factory.Object.Enemies;

import com.ProgPro.Factory.Attack.Attack;
import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.PhysicsObject;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.State.State;
import com.ProgPro.State.Stateful;
import com.ProgPro.Strategy.CombatEnemy.EnemyCombatStrategy;
import com.ProgPro.Strategy.Movement.Movable;
import com.ProgPro.Strategy.Movement.MovementStrategy;

public abstract class Enemy extends PhysicsObject implements Movable {

    private boolean isInCombat;
    private boolean isAlive;
    private int life;
    private int maxLife;
    private Attack currentAttack;
    private State state;
    private MovementStrategy movementStrategy;
    private EnemyCombatStrategy enemyCombatStrategy;

    public Enemy(int x, int y, String name) {
        super(x, y, name);
        life = 8;
        maxLife = life;
        isAlive = true;
    }

    public boolean isAlive() { return isAlive; }
    public boolean isInCombat() { return isInCombat; }
    public void setInCombat(boolean  isInCombat) { this.isInCombat = isInCombat; }
    public int getLife() { return life; }
    public void setEnemyCombatStrategy(EnemyCombatStrategy strategy){ this.enemyCombatStrategy = strategy; }

    public void heal(int amount){
         life = life + amount;
    }

    public Attack getAttack(){
        return currentAttack;
    }
    public int getMaxLife() { return maxLife; }
    public State getState(){
        return state;
    }

    public void setState(State state){
        this.state = state;
    }
    protected void setCurrentAttack(Attack attack) {
        this.currentAttack = attack;
    }
    protected EnemyCombatStrategy getEnemyCombatStrategy() {
        return enemyCombatStrategy;
    }
    public void setLife(int life) {
        this.life = life;
    }

    public abstract CombatAction decideTurn(Player player);

    public void takeDamage(String attackName, int amount) throws Exception {
        life -= amount;
        if(life <= 0){
            isAlive =  false;
            GameEventBus.getInstance().publish(GameEvent.ENEMY_DIED,  super.getName() + " est mort");
        }else{
            GameEventBus.getInstance().publish(GameEvent.ENEMY_HIT, "Vous lancez " + attackName + ". " + super.getName() + " subit " + amount + " dégâts.");
        }
    }

    public void moveBack(Player player){
        if(player.getX() > getX()){
            float newX = getBody().getPosition().x - 1f;
            getBody().setTransform(newX, getBody().getPosition().y, 0);
        }else{
            float newX = getBody().getPosition().x + 1f;
            getBody().setTransform(newX, getBody().getPosition().y, 0);
        }
    }

    public void moveForward(Player player){
        if(player.getX() > getX()){
            float newX = getBody().getPosition().x + 1f;
            getBody().setTransform(newX, getBody().getPosition().y, 0);
        }else{
            float newX = getBody().getPosition().x - 1f;
            getBody().setTransform(newX, getBody().getPosition().y, 0);
        }
    }

    public int getCombatDistance(){
        return 20;
    }

    public void kill() {
        isAlive = false;
    }

    @Override
    public float getHorizontalDirection(){
        return 0;
    }
}
