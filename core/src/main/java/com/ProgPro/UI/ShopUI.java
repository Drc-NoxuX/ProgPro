package com.ProgPro.UI;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.PNJ.MerchandPNJ;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Object.Usable.Pickable;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ShopUI {
    private Stage stage;
    private Skin skin;
    private Table table;
    private Player player;
    private MerchandPNJ merchandPNJ;
    private Label labelGold;
    private Label labelPotion;
    private Label labelGrandePotion;

    public ShopUI(Player player, MerchandPNJ merchandPNJ) {
        this.player = player;
        this.merchandPNJ = merchandPNJ;
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        table = new Table();
        Label titleLabel = new Label("Boutique", skin);
        table.add(titleLabel).padTop(10);
        table.row();
        labelGold = new Label("Votre argent : " + player.getInventory().getGold(), skin);
        table.add(labelGold);
        table.row();
        labelPotion = new Label("Potions en stock : " + merchandPNJ.countStock("Potion"), skin);
        table.add(labelPotion);
        table.row();
        labelGrandePotion = new Label("Grandes Potions en stock : " + merchandPNJ.countStock("Grande Potion"), skin);
        table.add(labelGrandePotion);
        table.row();
        TextButton btnPotion = new TextButton("Acheter Potion (5 or)", skin);
        btnPotion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pickable item = merchandPNJ.getItemFromStock("Potion");
                if(item != null && player.getInventory().getGold() >= merchandPNJ.getPrice(item)) {
                    player.getInventory().removeGold(merchandPNJ.getPrice(item));
                    player.getInventory().addItem(item);
                    merchandPNJ.removeFromStock(item);
                }
            }
        });
        table.add(btnPotion);
        table.row();
        TextButton btnGPotion = new TextButton("Acheter Grande Potion (10 or)", skin);
        btnGPotion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pickable item = merchandPNJ.getItemFromStock("Grande Potion");
                if(item != null && player.getInventory().getGold() >= merchandPNJ.getPrice(item)) {
                    player.getInventory().removeGold(merchandPNJ.getPrice(item));
                    player.getInventory().addItem(item);
                    merchandPNJ.removeFromStock(item);
                }
            }
        });
        table.add(btnGPotion);
        table.row();
        TextButton btnFermer = new TextButton("Fermer", skin);
        btnFermer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    GameEventBus.getInstance().publish(GameEvent.SHOP_CLOSED, "");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        table.add(btnFermer);
        table.row();
        table.setFillParent(true);
        stage = new Stage();
        stage.addActor(table);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public void update(Player player) {
        labelGold.setText("Votre argent : " + player.getInventory().getGold());
        labelPotion.setText("Potions en stock : " + merchandPNJ.countStock("Potion"));
        labelGrandePotion.setText("Grandes Potions en stock : " + merchandPNJ.countStock("Grande Potion"));
    }

    public Stage getStage() {
        return stage;
    }
}
