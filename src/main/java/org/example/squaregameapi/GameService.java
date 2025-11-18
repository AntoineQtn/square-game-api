package org.example.squaregameapi;

import fr.le_campus_numerique.square_games.engine.Game;

public interface GameService {

    String createGame(String gameType, int playerCount, int boardSize);
    Object getGame(String gameId);
    Game playMove(String gameId, int x, int y);
}
