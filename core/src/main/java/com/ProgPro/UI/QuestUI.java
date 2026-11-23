package com.ProgPro.UI;

import com.ProgPro.Factory.Quest.Quest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.util.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class QuestUI {
    private Stage stage;
    private Skin skin;
    private Table table;

    public QuestUI() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        table = new Table();
        table.top().right();
        table.setFillParent(true);
        stage.addActor(table);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public void update(List<Quest> quests) {
        table.clear();
        Label activeQuest = new Label("-- Quêtes Actives --", skin);
        table.add(activeQuest);
        table.row();
        if(!quests.isEmpty()){
            for(Quest quest : quests){
                table.add(new Label(quest.getName() + " : " + quest.getDescription(), skin));
                table.row();
            }
        }else{
            table.add(new Label("Aucune quête active", skin));
            table.row();
        }
    }

}
