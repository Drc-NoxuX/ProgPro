package com.ProgPro.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputManager {
    private static InputManager instance;

    private InputManager(){
    }

    public static InputManager getInstance(){
        if(instance == null){
            instance = new InputManager();
        }
        return instance;
    }

    public static boolean isMoveLeftPressed(){
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.Q);
    }

    public static boolean isMoveRightPressed(){
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
    }

    public static boolean isRunPressed(){
        return Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
    }

    public static boolean isJumpJustPressed(){
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }

    public static boolean isInteractJustPressed(){
        return Gdx.input.isKeyJustPressed(Input.Keys.E);
    }

    public static boolean isInventoryJustPressed(){
        return Gdx.input.isKeyJustPressed(Input.Keys.I);
    }

    public static boolean isEscapeJustPressed(){
        return Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE);
    }
}
