package com.ProgPro.State;

//Interface qui dit que l'objet peut avoir un état qui change et qui modifie son comportement (comme un contrat)
public interface Stateful {
    void setState(State state);
    State getState();
    void revive() throws Exception;
}
