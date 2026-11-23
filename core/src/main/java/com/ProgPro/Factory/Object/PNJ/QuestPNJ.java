package com.ProgPro.Factory.Object.PNJ;

import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Quest.Quest;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.GameEvent.GameEventObserver;
import com.ProgPro.Managers.DialogManager;
import com.ProgPro.Managers.QuestManager;
import com.ProgPro.State.DialogState;

public class QuestPNJ extends PNJ implements GameEventObserver {

    private Quest quest;

    public QuestPNJ(int x, int y, String name) {
        super(x, y, name);
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    @Override
    public void interact(Player player) throws Exception {
        DialogManager.getInstance().setDialog(getDialog());
        player.setState(new DialogState(player));
        player.setIsInDialog(true);
        GameEventBus.getInstance().publish(GameEvent.DIALOG_STARTED, "");
        GameEventBus.getInstance().subscribe(this);
    }

    @Override
    public void displayEvent(GameEvent event, String message) throws Exception {
        if(event == GameEvent.DIALOG_FINISHED) {
            QuestManager.getInstance().addQuest(quest);
            GameEventBus.getInstance().unsubscribe(this);
        }
    }


}
