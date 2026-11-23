package com.ProgPro.UI;

import com.ProgPro.Factory.Object.Player;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.GameEvent.GameEventObserver;
import com.ProgPro.Managers.RessourceManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOverUI implements GameEventObserver {
    private Stage stage;
    private Skin skin;
    private Table table;
    private Label label;
    private Player player;

    public GameOverUI() throws Exception {
        player = RessourceManager.getInstance().getPlayer();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        label = new Label("Voulez-vous respawn ?", skin);
        table = new Table();
        table.add(label).colspan(2);
        table.row();
        TextButton textButtonRevive = new TextButton("Respawn", skin);
        textButtonRevive.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    GameEventBus.getInstance().publish(GameEvent.PLAYER_REVIVE, "Vous avez respawn");
                    player.setIsCombat(false);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        TextButton quit = new TextButton("Quitter", skin);
        table.add(textButtonRevive);
        table.add(quit);
        stage.addActor(table);
        table.setFillParent(true);
    }

    public void render(){
        stage.act();
        stage.draw();

    }
    @Override
    public void displayEvent(GameEvent event, String message) throws Exception {

    }

    public InputProcessor getStage() {
        return  stage;
    }
}
