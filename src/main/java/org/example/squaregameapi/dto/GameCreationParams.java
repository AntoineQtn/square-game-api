package org.example.squaregameapi.dto;

public class GameCreationParams {

    private String gameType;
    private int playerCount;
    private int boardSize;

    public String getGameType() {
        return gameType;
    }
    public int getPlayerCount() {
        return playerCount;
    }
    public int getBoardSize() {
        return boardSize;
    }
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
