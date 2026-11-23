package com.ProgPro.Factory.Object.PNJ;

public class PNJFactory {

    public static PNJ createPNJ(String type, String name, int x, int y){
        return switch (type) {
            case "merchand" -> new MerchandPNJ(x, y, name);
            case "basic" -> new BasicPNJ(x, y, name);
            case "quest" -> new QuestPNJ(x, y, name);
            default -> throw new IllegalArgumentException("Invalid type");
        };
    }
}
