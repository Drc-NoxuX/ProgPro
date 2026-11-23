package com.ProgPro.Factory.Object.Room;

import com.ProgPro.Factory.Object.Enemies.Enemy;
import com.ProgPro.Factory.Object.PNJ.PNJ;

import java.util.ArrayList;
import java.util.List;

public class PacificRoom extends Room {

    private List<PNJ> pnjList;

    public PacificRoom(String name, String path) {
        super(name, path);
        pnjList = new ArrayList<>();
    }

    @Override
    public List<PNJ> getPNJs() {
        return pnjList;
    }

    @Override
    public List<Enemy> getEnemys() {
        return new ArrayList<Enemy>();
    }

    public void  addPNJ(PNJ pnj){
        pnjList.add(pnj);
    }

}
