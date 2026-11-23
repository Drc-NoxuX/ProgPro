package com.ProgPro.Factory.Object.Room;

public class RoomFactory {

    public static Room createRoom(String roomType, String roomName, String roomPath){
        return switch (roomType) {
            case "Combat" -> new CombatRoom(roomName, roomPath);
            case "Pacific" -> new PacificRoom(roomName, roomPath);
            default -> throw new IllegalArgumentException("Invalid room type");
        };
    }
}
