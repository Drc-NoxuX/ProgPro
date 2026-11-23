package com.ProgPro.Facade;

import com.ProgPro.Factory.Object.Player;
import com.ProgPro.Factory.Object.Room.Room;
import com.ProgPro.Managers.RessourceManager;
import com.badlogic.gdx.physics.box2d.World;

public class FacadeWorld {
    private Player player;
    private World world;

    public FacadeWorld() throws Exception {
        player = RessourceManager.getInstance().getPlayer();
        world = RessourceManager.getInstance().getWorld();
    }

    public Player getPlayer() {
        return player;
    }
    public World getWorld() {
        return world;
    }

    public void update(){
        world.step(1/60f,6,2);
        player.update();
    }

    public Room getRoom() throws Exception {
        return RessourceManager.getInstance().getRoom();
    }
}
