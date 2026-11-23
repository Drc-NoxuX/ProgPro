package com.ProgPro.UI;

import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EnemyHealthUI {
    private ShapeRenderer shapeRenderer;
    private Enemy enemy;

    public EnemyHealthUI(){
        shapeRenderer = new ShapeRenderer();
    }

    public void setEnemy(Enemy enemy){
        this.enemy = enemy;
    }

    public void render(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(enemy.getX(), enemy.getY() + 70, 200, 20 );
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(enemy.getX(), enemy.getY() + 70, 200*enemy.getLife() /(float) enemy.getMaxLife(), 20 );
        shapeRenderer.end();

    }
}
