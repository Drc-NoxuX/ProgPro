package com.ProgPro.UI;

import com.ProgPro.Factory.Object.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealthUI {

    private ShapeRenderer shapeRenderer;
    private Player player;

    public HealthUI(ShapeRenderer shapeRenderer, Player player) {
        this.shapeRenderer = shapeRenderer;
        this.player = player;
    }

    public void render(){
        int firstX = 20;
        int y = 740;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i < player.getMaxLife(); i++){
            if(i < player.getLife()){
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.circle(firstX, y, 10);
            }
            firstX += 30;
        }
        shapeRenderer.end();

        firstX = 20;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i < player.getMaxLife(); i++){
            if(i >= player.getLife()){
                shapeRenderer.setColor(Color.LIGHT_GRAY);
                shapeRenderer.circle(firstX, y, 10);
            }
            firstX += 30;
        }
        shapeRenderer.end();

        firstX = 20;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(int i = 0; i < player.getMaxLife(); i++){
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(firstX, y, 12);
            firstX += 30;
        }
        shapeRenderer.end();
    }
}
