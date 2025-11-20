package org.example.squaregameapi.dto;

import lombok.Getter;

/**
 * Represents the parameters required for creating a new game.
 * This class is used to specify the initial configuration for a game,
 * including the type of the game, number of players, and the board size.
 * <p>
 * Instances of this class can be used to transfer game creation settings
 * to methods or APIs that handle game initialization.
 * <p>
 * The class includes the following properties:
 * - gameType: Specifies the type of game to be created.
 * - playerCount: Defines the number of players for the game.
 * - boardSize: Indicates the size of the game board.
 */
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
