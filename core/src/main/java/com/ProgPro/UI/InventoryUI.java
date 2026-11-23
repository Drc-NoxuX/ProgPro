package com.ProgPro.UI;

import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Object.Usable.Pickable;
import com.ProgPro.Factory.Object.Usable.Potion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class InventoryUI {
    private Stage stage;
    private Skin skin;
    private Table table;
    private Label labelInventaire;
    private Label labelGold;
    private Label labelPotion;
    private Label labelGPotion;
    private TextButton textButton1;
    private Player player;

    public InventoryUI(Player player) {
        this.player = player;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        labelInventaire = new Label("Inventaire", skin);
        textButton1 = new TextButton("Combiner", skin);
        textButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.getInventory().combine();
            }
        });
        labelGold = new Label("Gold", skin);
        labelPotion = new Label("Potions", skin);
        labelGPotion = new Label("", skin);
        table = new Table();
        table.add(labelInventaire).colspan(2);
        table.row();
        table.add(labelGold).colspan(2);
        table.row();
        table.add(labelPotion).colspan(2);
        table.add(labelGPotion).colspan(2);
        table.row();
        table.row();
        table.add(textButton1).colspan(2);
        stage.addActor(table);
        table.setFillParent(true);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public void update(Player player){
        labelGold.setText("Or : " + player.getInventory().getGold());
        int potionCount = 0;
        int gPotionCount = 0;
        for(Pickable item : player.getInventory().getItems()){
            if(item instanceof Potion){
                if(((Potion)item).getName().equals("Grande Potion")){
                    gPotionCount++;
                } else {
                    potionCount++;
                }
            }
        }
        labelPotion.setText("Potions : " + potionCount);
        labelGPotion.setText("Grandes Potions : " + gPotionCount);
    }

    public Stage getStage() {
        return stage;
    }
}
