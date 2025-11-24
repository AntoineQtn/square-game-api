package org.example.squaregameapi.service;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.Token;
import org.example.squaregameapi.dao.GameDAO;
import org.example.squaregameapi.dao.MoveDAO;
import org.example.squaregameapi.plugin.GamePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of the GameService interface responsible for managing and interacting with game sessions.
 * This service allows the creation of new games using plugins, retrieval of active game sessions,
 * and performing moves within a game.
 */
@Service
public class GameServiceImpl implements GameService {

    // Stockage des sessions en Map
//    private Map<String, Game> games = new HashMap<>();
    @Autowired
    private GameDAO gameDao;

    @Autowired
    private MoveDAO moveDAO;

    // Stockage des factories en Map
    //private Map<String, GameFactory> factories = new HashMap<>();

    // Stockage des plugins en Map
    private Map<String, GamePlugin> plugins = new HashMap<>();

    // Compteur de coups par partie (en mémoire pour simplifier)
    private Map<String, Integer> moveCounters = new HashMap<>();

    @Autowired
    public GameServiceImpl(List<GamePlugin> pluginList) {
        pluginList.forEach(plugin -> plugins.put(plugin.getGameType(), plugin));
    }

//    public GameServiceImpl() {
//        // Initialisation des factories
//        factories.put("tic-tac-toe", new TicTacToeGameFactory());
//    }

    @Override
    public String createGame(String gameType, int playerCount, int boardSize) {

        GamePlugin plugin = plugins.get(gameType);

        if (plugin == null) {
            return null;
        }

        OptionalInt optPlayerCount = OptionalInt.of(playerCount);
        OptionalInt optBoardSize = OptionalInt.of(boardSize);

        Game game = plugin.createGame(optPlayerCount, optBoardSize);

        String gameId = UUID.randomUUID().toString();

        gameDao.save(game);
//        games.put(gameId, game);

        return gameId;
    }

    @Override
    public Object getGame(String gameId) {
//        return games.get(gameId);
        return gameDao.findById(gameId);
    }

    @Override
    public Game playMove(String gameId, int x, int y) {

//        GameDAO game = games.get(gameId);/
        Game game = gameDao.findById(gameId);
        if (game == null) {
            return null;
        }

        Collection<Token> remainingTokens = game.getRemainingTokens();

        Token currentToken = remainingTokens.iterator().next();

        CellPosition position = new CellPosition(x, y);

        try {
            currentToken.moveTo(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        gameDao.update(game);
        // Déterminer l'ordre du coup
        int moveOrder = moveCounters.getOrDefault(gameId, 0) + 1;
        moveCounters.put(gameId, moveOrder);

        // Enregistrer le coup dans la table moves
        moveDAO.saveMove(gameId, moveOrder, x, y);
        return game;
    }
}
