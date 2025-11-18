package org.example.squaregameapi.dto;

import lombok.Getter;

@Getter
public class GameCreationParams {

    private String gameType;
    private int playerCount;
    private int boardSize;

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
}
