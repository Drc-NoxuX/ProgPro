package com.ProgPro.UI;

import com.ProgPro.Facade.FacadeManager;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.Save.SaveManager;
import com.ProgPro.Screen.GameScreen;
import com.ProgPro.Screen.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseMenuUI {
    private Stage stage;
    private Skin skin;
    private FacadeManager facadeManager;
    private Game game;

    public PauseMenuUI(Game game, FacadeManager facadeManager) {
        this.game = game;
        this.facadeManager = facadeManager;
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        stage = new Stage();
    }

    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        TextButton resumeButton = new TextButton("Reprendre", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    GameEventBus.getInstance().publish(GameEvent.PAUSE_FINISHED, "Le jeu reprend");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        table.add(resumeButton).row();

        TextButton saveButton = new TextButton("Sauvegarder", skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showSlots();
            }
        });
        table.add(saveButton).row();

        TextButton mainMenuButton = new TextButton("Menu Principal", skin);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        table.add(mainMenuButton).row();
        stage.addActor(table);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void showSlots() {
        stage.clear();
        Table table = new Table();
        table.setFillParent(true);
        for(int i = 1; i <= 6; i++) {
            String label = SaveManager.getInstance().doesSlotExist(i)
                ? "Slot " + i + " — Occupé"
                : "Slot " + i + " — Vide";
            TextButton slotButton = new TextButton(label, skin);
            final int slotNumber = i;
            slotButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                  try{
                      SaveManager.getInstance().save(facadeManager.buildSaveData(), slotNumber);
                  } catch (Exception e) {
                      throw new RuntimeException(e);
                  }
                }
            });
            table.add(slotButton).row();
        }
        TextButton backButton = new TextButton("Retour", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show();
            }
        });
        table.add(backButton).row();
        stage.addActor(table);
    }
}
