package org.example.squaregameapi.service;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.Token;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.example.squaregameapi.GamePlugin;
import org.example.squaregameapi.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    // Stockage des sessions en Map
    private Map<String, Game> games = new HashMap<>();

    // Stockage des factories en Map
    //private Map<String, GameFactory> factories = new HashMap<>();

    // Stockage des plugins en Map
    private Map<String, GamePlugin> plugins = new HashMap<>();

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
        // Récupérer la factory selon gameType
        //GameFactory factory = factories.get(gameType);

        GamePlugin plugin = plugins.get(gameType);

        // Vérifier qu'elle existe
        if (plugin == null) {
            // Pour l'instant retourne null plus tard Exception
            return null;
        }

        // Convertir en OptionalInt (pour l'instant, toujours présents)
        OptionalInt optPlayerCount = OptionalInt.of(playerCount);
        OptionalInt optBoardSize = OptionalInt.of(boardSize);

        // Créer le jeu
        Game game = plugin.createGame(optPlayerCount, optBoardSize);

        // Générer un ID unique
        String gameId = UUID.randomUUID().toString();

        // Stocker le jeu dans la Map
        games.put(gameId, game);

        return gameId;
    }

    @Override
    public Object getGame(String gameId) {
        // Récupérer le jeu depuis la Map avec gameId
        return games.get(gameId);
    }

    @Override
    public Game playMove(String gameId, int x, int y) {

        Game game = games.get(gameId);

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
        return game;
    }
}
