package com.ProgPro.Factory.Object.Enemies;

public class EnemyFactory {

    public static Enemy create(String type, int x, int y) {
        return switch (type) {
            case "close" -> new CloseCombatEnemy(x, y, "Le Furieux");
            case "far" -> new FarCombatEnemy(x, y, "Le Paria");
            default -> throw new IllegalArgumentException("Unknow enemy type " + type);
        };
    }
}
