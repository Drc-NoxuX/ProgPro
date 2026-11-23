package com.ProgPro.UI;

import com.ProgPro.Factory.Object.Player;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.GameEvent.GameEventObserver;
import com.ProgPro.Strategy.Combat.TurnBasedCombat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CombatUI implements GameEventObserver {
    private Stage stage;
    private Skin skin;
    private Table table;
    private Label label;
    private TextButton textButton1;
    private TextButton textButton2;
    private TextButton textButton3;
    private Player player;
    private TurnBasedCombat  turnBasedCombat;

    public CombatUI(Player player) {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        label = new Label("texte", skin);
        textButton1 = new TextButton("Fire Attack", skin);
        textButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (turnBasedCombat != null && !textButton1.isDisabled()) {
                    turnBasedCombat.setCurrentAttack(player.getFireAttack());
                }
            }
        });
        textButton2 = new TextButton("Water Attack", skin);
        textButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (turnBasedCombat != null && !textButton2.isDisabled()) {
                    turnBasedCombat.setCurrentAttack(player.getWaterAttack());
                }
            }
        });
        textButton3 = new TextButton("Potion", skin);
        textButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (turnBasedCombat != null && !textButton3.isDisabled()) {
                    try {
                        turnBasedCombat.usePotion();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        table = new Table();
        table.add(label).colspan(2);
        table.row();
        table.add(textButton1).width(150).height(30);
        table.add(textButton2).width(150).height(30);
        table.add(textButton3).width(150).height(30);
        stage.addActor(table);
        table.setFillParent(true);
        table.bottom();
        GameEventBus.getInstance().subscribe(this);
        this.player = player;
    }

    public void setTurnBasedCombat(TurnBasedCombat combat) {
        turnBasedCombat = combat;
    }

    public void render(){
        stage.act();
        stage.draw();

    }

    @Override
    public void displayEvent(GameEvent event, String message) {
        label.setText(message);
        textButton1.setDisabled(true);
        textButton2.setDisabled(true);
        textButton3.setDisabled(true);
        if(event == GameEvent.PLAYER_TURN){
            textButton1.setDisabled(false);
            textButton2.setDisabled(false);
            textButton3.setDisabled(false);
        }
        if(event == GameEvent.COMBAT_STARTED){
            textButton1.setDisabled(false);
            textButton2.setDisabled(false);
            textButton3.setDisabled(false);
        }
        if(event == GameEvent.PLAYER_LOST){
            table.clear();
        }else if(event == GameEvent.PLAYER_WON){
            player.setIsCombat(false);
        }
    }

    public InputProcessor getStage() {
        return stage;
    }
}
