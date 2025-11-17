package org.example.squaregameapi.dto;

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
