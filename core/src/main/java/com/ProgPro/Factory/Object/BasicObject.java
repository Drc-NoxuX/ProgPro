package com.ProgPro.Factory.Object;

public abstract class BasicObject {
    private int x;
    private int y;
    private String name;

    public BasicObject(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance(BasicObject concernedObject){
        int xDistance = concernedObject.getX() - this.getX();
        int yDistance = concernedObject.getY() - this.getY();
        return (int) Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }
}
