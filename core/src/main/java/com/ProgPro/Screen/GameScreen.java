package com.ProgPro.Screen;

import com.ProgPro.Facade.FacadeManager;
import com.ProgPro.Managers.QuestManager;
import com.ProgPro.Managers.RessourceManager;
import com.ProgPro.Save.SaveData;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    private FacadeManager facadeManager;
    private SaveData saveData;
    private Game game;

    public GameScreen(SaveData saveData, Game game) {
        this.saveData = saveData;
        this.game = game;
    }

    @Override
    public void show() {
        RessourceManager.reset();
        QuestManager.reset();
        try {
            facadeManager = new FacadeManager(game);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(saveData != null) {
            try {
                facadeManager.applySaveData(saveData);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        try {
            facadeManager.update();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        facadeManager.dispose();
    }
}
