package org.example.squaregameapi.dto;

/**
 * Represents basic information about a game.
 * This class is used to encapsulate the game's unique identifier and its name.
 * It is typically used as part of game-related data transfer objects or
 * responses in a game API.
 */
public class GameInfo {

    private String Id;
    private String name;

    public GameInfo(String id, String name) {
        this.Id = id;
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }
}
