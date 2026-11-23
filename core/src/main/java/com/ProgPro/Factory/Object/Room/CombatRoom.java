package com.ProgPro.Factory.Object.Room;

import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.PNJ.PNJ;
import com.ProgPro.Managers.RessourceManager;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class CombatRoom extends Room{

    private List<Enemy> enemies;

    public CombatRoom(String name, String path) {
        super(name, path);
        enemies = new ArrayList<>();
    }

    @Override
    public List<PNJ> getPNJs() {
        return new ArrayList<PNJ>();
    }

    @Override
    public List<Enemy> getEnemys() {
        return enemies;
    }

    public void addEnemy(Enemy enemy, World world) throws Exception {
        enemies.add(enemy);
        enemy.createBody(world, 32, 48, BodyDef.BodyType.KinematicBody);
    }
}
