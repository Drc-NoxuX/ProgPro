package com.ProgPro.Facade;

import com.ProgPro.Factory.Object.BasicObject;
import com.ProgPro.Factory.Object.PNJ.PNJ;
import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Object.Room.Room;
import com.ProgPro.Factory.Object.Usable.Pickable;
import com.ProgPro.GameEvent.GameEvent;
import com.ProgPro.GameEvent.GameEventBus;
import com.ProgPro.Managers.RessourceManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Iterator;

public class FacadeInteraction {
    private Player player;
    private FacadeRender render;

    public FacadeInteraction(FacadeRender render) throws Exception {
        player = RessourceManager.getInstance().getPlayer();
        this.render = render;
    }

    public void update() throws Exception {
        render.setNearbyPNJ(null);
        int rangePNJ = 100;
        for(PNJ pnj : RessourceManager.getInstance().getRoom().getPNJs()){
            if (pnj.getDistance(player) <= rangePNJ){
                render.setNearbyPNJ(pnj);
                if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                    pnj.interact(player);
                    break;
                }
            }
        }

        int rangeItem = 10;
        Room room = RessourceManager.getInstance().getRoom();
        Iterator<Pickable> it = room.getItems().iterator();
        while(it.hasNext()) {
            Pickable item = it.next();
            BasicObject itemAsObject = (BasicObject) item;
            if(itemAsObject.getDistance(player) <= rangeItem) {
                item.onPickup(player);
                it.remove();
                GameEventBus.getInstance().publish(GameEvent.ITEM_PICKED, itemAsObject.getName() + " ramassé(e) !");
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            render.setInventoryOpen(!render.getInventoryOpen());
        }

        updateRoom();
    }

    public void updateRoom() throws Exception {
        Room oldRoom = RessourceManager.getInstance().getRoom();
        if(player.getX() > 1024 && RessourceManager.getInstance().getRoom().getRightRoom() != null){
            Room nextRoom = RessourceManager.getInstance().getRoom().getRightRoom();
            RessourceManager.getInstance().deactivateRoom(oldRoom);
            RessourceManager.getInstance().activateRoom(nextRoom);
            RessourceManager.getInstance().setRoom(nextRoom);
            render.changeRoom(nextRoom);
            player.teleport(50, player.getY());
        }else if (player.getX() < 0 && RessourceManager.getInstance().getRoom().getLeftRoom() != null){
            Room nextRoom = RessourceManager.getInstance().getRoom().getLeftRoom();
            RessourceManager.getInstance().deactivateRoom(oldRoom);
            RessourceManager.getInstance().activateRoom(nextRoom);
            RessourceManager.getInstance().setRoom(nextRoom);
            render.changeRoom(nextRoom);
            player.teleport(974, player.getY());
        }else if(player.getY() <= 0 && RessourceManager.getInstance().getRoom().getDownRoom() != null ){
            Room nextRoom = RessourceManager.getInstance().getRoom().getDownRoom();
            RessourceManager.getInstance().deactivateRoom(oldRoom);
            RessourceManager.getInstance().activateRoom(nextRoom);
            RessourceManager.getInstance().setRoom(nextRoom);
            render.changeRoom(nextRoom);
            player.teleport(player.getX(), 500);
        }
    }
}
