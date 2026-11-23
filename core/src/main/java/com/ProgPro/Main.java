package com.ProgPro;

import com.ProgPro.Facade.FacadeManager;
import com.ProgPro.Facade.FacadeWorld;
import com.ProgPro.Screen.MainMenuScreen;
import com.ProgPro.UI.CombatUI;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private MainMenuScreen mainMenuScreen;

    @Override
    public void create() {
        mainMenuScreen = new MainMenuScreen(this);
        setScreen(mainMenuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        mainMenuScreen.dispose();
    }
}
