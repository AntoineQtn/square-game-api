package org.example.squaregameapi;

public interface GameService {

    String createGame(String gameType, int playerCount, int boardSize);
    Object getGame(String gameId);

}
