package com.ProgPro.Strategy.Movement;

import com.ProgPro.Factory.Object.BasicObject;

public interface MovementStrategy{
    boolean canTransitionTo(MovementStrategy movementStrategy);
    void setStrategy(BasicObject basicObject);
}
