package org.example.squaregameapi.service;

import fr.le_campus_numerique.square_games.engine.Game;

/**
 * Defines the core interface for managing game sessions and interactions.
 * The GameService provides functionalities for creating new games, retrieving game
 * instances by identifier, and performing game moves.
 */
public interface GameService {

    String createGame(String gameType, int playerCount, int boardSize);
    Object getGame(String gameId);
    Game playMove(String gameId, int x, int y);

}
