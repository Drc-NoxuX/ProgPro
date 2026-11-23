package com.ProgPro.Factory.Object;

import com.ProgPro.Factory.Attack.Attack;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.Inventory;
import com.ProgPro.State.AliveState;
import com.ProgPro.State.State;
import com.ProgPro.State.Stateful;
import com.ProgPro.Strategy.Movement.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Player extends PhysicsObject implements Stateful, Movable {

    private  int life;
    private int maxLife;
    private Attack waterAttack;
    private Attack fireAttack;
    private Inventory inventory;
    private boolean isAlive;
    private boolean isInCombat;
    private boolean isInDialog;
    private boolean isInShop;
    private MovementStrategy movementStrategy;
    private boolean onGround = false;
    private final WalkStrategy walkStrategy = new WalkStrategy(); //final, car on en change jamais
    private final JumpStrategy jumpStrategy = new JumpStrategy();
    private final IdleStrategy idleStrategy = new IdleStrategy();
    private final FallStrategy fallStrategy = new FallStrategy();
    private final RunStrategy runStrategy = new RunStrategy();
    private State state;


    public Player(int x, int y, String name) {
        super(x, y, name);
        life = 8;
        maxLife = 8;
        waterAttack = new Attack("Bubble", 300, 8);
        fireAttack = new Attack("Fire", 200, 8);
        inventory = new Inventory();
        setState(new AliveState(this));
        movementStrategy = idleStrategy;
        isAlive = true;
        isInDialog = false;
    }

    public int getLife() { return life; }
    public boolean isAlive() { return isAlive; }
    public boolean isInCombat() { return isInCombat; }
    public boolean isInDialog() { return isInDialog; }
    public boolean isInShop() { return isInShop; }

    public Attack getFireAttack() {
        return fireAttack;
    }
    public Attack getWaterAttack(){
        return waterAttack;
    }

    public void setIsCombat(boolean isCombat) { this.isInCombat = isCombat; }
    public void setIsAlive(boolean isAlive) { this.isAlive = isAlive; }
    public void setIsInDialog(boolean isInDialog) { this.isInDialog = isInDialog; }
    public void setIsInShop(boolean isInShop) { this.isInShop = isInShop; }
    public void setLife(int life){
        this.life = life;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public State getState() {
        return state;
    }

    public int getMaxLife() { return maxLife; }

    @Override
    public void revive() {
        life = maxLife;
    }

    public void update() {
        if(super.getBody() == null){
            return;
        }
        if(!isInCombat){
            updateMovementStrategy();
            movementStrategy.setStrategy(this);
        }
    }

    private void updateMovementStrategy(){
        Vector2 vel = super.getBody().getLinearVelocity();
        MovementStrategy next;
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && onGround){
            jumpStrategy.reset();
            next = jumpStrategy;
        }else if(!onGround && vel.y < - 0.5f){ //Si on est dans les airs et qu'on va vers le bas
            next = fallStrategy;
        }else if(!onGround){//Dans les airs mais pas encore en chute
            next = jumpStrategy;
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.Q) || Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                next = runStrategy;
            }else{
                next = walkStrategy;
            }
        }else{
            next = idleStrategy;
        }
        if(next != movementStrategy && movementStrategy.canTransitionTo(next)){
            movementStrategy = next;
        }
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void takeDamage(String enemyName, String attackName, int amount) throws Exception {
        life -= amount;
        if(life <= 0){
            isAlive =  false;
            GameEventBus.getInstance().publish(GameEvent.PLAYER_DIED, "Vous êtes morts");
        }else{
            GameEventBus.getInstance().publish(GameEvent.PLAYER_HIT, enemyName + " lance" + attackName + "et inflige " + amount + " dégâts");
        }
    }

    public void heal(int amount){
        life += amount;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void teleport(float x, float y){
        super.getBody().setTransform(x/PPM, y/PPM, super.getBody().getAngle());
    }

    @Override
    public float getHorizontalDirection() {
        return Gdx.input.getX();
    }
}
