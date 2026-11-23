package com.ProgPro.Save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public class SaveManager {
    private static SaveManager instance;
    private final static int MAX_SLOTS = 6;

    private SaveManager(){
    }

    public static SaveManager getInstance(){
        if(instance == null){
            instance = new SaveManager();
        }
        return instance;
    }

    public void save(SaveData data, int slot){
       Json json = new Json();
       String dataJson = json.toJson(data);
       Gdx.files.local("save_" + slot + ".json").writeString(dataJson, false);
    }

    public SaveData load(int slot){
        String loadData = Gdx.files.local("save_" + slot + ".json").readString();
        return new Json().fromJson(SaveData.class, loadData);
    }

    public boolean doesSlotExist(int slot){
        return Gdx.files.local("save_" + slot + ".json").exists();
    }
}
