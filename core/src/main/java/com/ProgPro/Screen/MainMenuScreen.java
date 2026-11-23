package com.ProgPro.Screen;

import com.ProgPro.Save.SaveManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Game game;

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        TextButton newGameButton = new TextButton("Nouvelle Partie", skin);
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(null, game));
            }
        });
        TextButton loadButton = new TextButton("Charger", skin);
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showSlots();
            }
        });
        TextButton quitButton = new TextButton("Quitter", skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(newGameButton).row();
        table.add(loadButton).row();
        table.add(quitButton).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
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
                    if(SaveManager.getInstance().doesSlotExist(slotNumber)) {
                        game.setScreen(new GameScreen(SaveManager.getInstance().load(slotNumber), game));
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
