package com.ProgPro.Factory.Attack;

public class Attack {

    private int range;
    private int amountOfDamage;
    private String name;

    public Attack (String name,int range, int amountOfDamage) {
        setAmountOfDamage(amountOfDamage);
        setRange(range);
        this.name = name;
    }

    public void setAmountOfDamage(int amountOfDamage) {
        if(amountOfDamage > 0) {
            this.amountOfDamage = amountOfDamage;
        } else {
            throw new IllegalArgumentException("amountOfDamage cannot be negative");
        }
    }

    public int getAmountOfDamage() {
        return amountOfDamage;
    }

    public void setRange(int range) {
        if(range > 0) {
            this.range = range;
        }else{
            throw new IllegalArgumentException("range cannot be negative");
        }
    }

    public int getRange() {
        return range;
    }

    public String getName() {
        return name;
    }
}
