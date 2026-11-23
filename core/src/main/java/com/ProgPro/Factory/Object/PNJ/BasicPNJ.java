package com.ProgPro.Factory.Object.PNJ;

import com.ProgPro.Factory.Object.Player;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.Managers.DialogManager;
import com.ProgPro.State.DialogState;

public class BasicPNJ extends PNJ {

    public BasicPNJ(int x, int y, String name) {
        super(x, y, name);
    }

    @Override
    public void interact(Player player) throws Exception {
        getDialog().reset();
        DialogManager.getInstance().setDialog(getDialog());
        player.setState(new DialogState(player));
        player.setIsInDialog(true);
        GameEventBus.getInstance().publish(GameEvent.DIALOG_STARTED, "");
    }
}
