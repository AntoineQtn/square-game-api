package org.example.squaregameapi.service;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.example.squaregameapi.GameService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    // Stockage des sessions en Map
    private Map<String, Game> games = new HashMap<>();

    // Stockage des factories en Map
    private Map<String, GameFactory> factories = new HashMap<>();

    public GameServiceImpl() {
        // Initialisation des factories
        factories.put("tic-tac-toe", new TicTacToeGameFactory());
    }

    @Override
    public String createGame(String gameType, int playerCount, int boardSize) {
        // Récupérer la factory selon gameType
        GameFactory factory = factories.get(gameType);

        // Vérifier qu'elle existe
        if (factory == null) {
            // Pour l'instant retourne null plus tard Exception
            return null;
        }

        // Créer le jeu
        Game game = factory.createGame(playerCount, boardSize);

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
}
