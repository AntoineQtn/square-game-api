package org.example.squaregameapi.service;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.Token;
import org.example.squaregameapi.entity.GameEntity;
import org.example.squaregameapi.entity.MoveEntity;
import org.example.squaregameapi.plugin.GamePlugin;
import org.example.squaregameapi.repository.GameRepository;
import org.example.squaregameapi.repository.MoveRepository;
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
//    @Autowired
//    private GameDAO gameDao;

//    @Autowired
//    private MoveDAO moveDAO;

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private MoveRepository moveRepository;

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

    /**
     * Creates a new game based on the given parameters.
     *
     * @param gameType    the type of the game to be created; must match a recognized game type
     * @param playerCount the number of players for the game; must be greater than zero
     * @param boardSize   the size of the board for the game; must be a positive integer
     * @param userId      the unique identifier of the user initiating the game creation
     * @return the unique identifier of the created game
     * @throws IllegalArgumentException if the provided game type is not recognized
     */
    @Override
    public String createGame(String gameType, int playerCount, int boardSize, String userId) {
        System.out.println("Creating game: " + gameType);
        System.out.println("Available: " + plugins.keySet());

        GamePlugin plugin = plugins.entrySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase(gameType.replace("-", "").replace("_", "")))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown game type: '" + gameType + "'. Available: " + plugins.keySet()
                ));

        OptionalInt optPlayerCount = OptionalInt.of(playerCount);
        OptionalInt optBoardSize = OptionalInt.of(boardSize);

        Game game = plugin.createGame(optPlayerCount, optBoardSize);
        String gameId = game.getId().toString();

        GameEntity gameEntity = new GameEntity(gameId, game.getFactoryId(), userId);
        gameRepository.save(gameEntity);

        System.out.println("Game created: " + gameId);

        return gameId;
    }

    /**
     * Retrieves a game by its unique identifier, reconstructing the game state
     * using the specified game type and move history from the repositories.
     *
     * @param gameId the unique identifier of the game to retrieve
     * @return the reconstructed game object, or null if the game or its plugin cannot be found
     */
    @Override
    public Object getGame(String gameId) {
//        return games.get(gameId);
        GameEntity gameEntity = gameRepository.findById(gameId).orElse(null);
        if (gameEntity == null) {
            return null;
        }
        GamePlugin plugin = findPlugin(gameEntity.getGameType());
        if (plugin == null) {
            return null;
        }
        Game game = plugin.createGame(OptionalInt.of(2), OptionalInt.empty());

        List<MoveEntity> moves = moveRepository.findByGameOrderByMoveOrderAsc(gameEntity);
        for (MoveEntity moveEntity : moves) {
            Collection<Token> remainingTokens = game.getRemainingTokens();
            if (!remainingTokens.isEmpty()) {
                Token token = remainingTokens.iterator().next();
                CellPosition position = new CellPosition(moveEntity.getX(), moveEntity.getY());
                try {
                    token.moveTo(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return game;
    }

    /**
     * Plays a move in the ongoing game. It validates the user's turn, updates the game state,
     * applies the move logic using the corresponding game plugin, and saves the move.
     *
     * @param gameId the unique identifier of the game in which the move is to be played
     * @param x the x-coordinate of the move to be played
     * @param y the y-coordinate of the move to be played
     * @param userId the unique identifier of the user playing the move
     * @return the updated game state after the move has been applied
     * @throws IllegalArgumentException if the game is not found, the plugin is missing,
     *                                  it is not the user's turn, the move is invalid,
     *                                  or tokens are unavailable
     * @throws IllegalStateException if no tokens are available for the current move
     */
    @Override
    public Game playMove(String gameId, int x, int y, String userId) {

        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
        List<MoveEntity> moves = moveRepository.findByGameOrderByMoveOrderAsc(gameEntity);
// Déterminer quel joueur doit jouer
        int moveCount = moves.size();
        boolean isPlayer1Turn = (moveCount % 2 == 0);
        String expectedPlayerId = isPlayer1Turn ? gameEntity.getPlayer1Id() : gameEntity.getPlayer2Id();

// Vérifier que c'est bien le bon joueur
        if (!expectedPlayerId.equals(userId)) {
            throw new IllegalArgumentException("Not your turn!");
        }

        GamePlugin plugin = findPlugin(gameEntity.getGameType());
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin not found");
        }

        Game game = plugin.createGame(OptionalInt.of(2), OptionalInt.empty());


//        GameDAO game = games.get(gameId);/
//        Game game = gameDao.findById(gameId);
//        Optional<GameEntity> optionalGame = gameRepository.findById(gameId);
//        if (optionalGame.isEmpty()) {
//            return null;
//        }
//        GameEntity gameEntity = optionalGame.get();
//
//        GamePlugin plugin = findPlugin(gameEntity.getGameType());
//        if (plugin == null) {
//            return null;
//        }
//        Game game = plugin.createGame(OptionalInt.of(2), OptionalInt.empty());
//        List<MoveEntity> moves = moveRepository.findByGameOrderByMoveOrderAsc(gameEntity);

        for (MoveEntity moveEntity : moves) {
            Collection<Token> remainingTokens = game.getRemainingTokens();
            if (!remainingTokens.isEmpty()) {
                Token token = remainingTokens.iterator().next();
                CellPosition position = new CellPosition(moveEntity.getX(), moveEntity.getY());
                try {
                    token.moveTo(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Collection<Token> remainingTokens = game.getRemainingTokens();
        if (remainingTokens.isEmpty()) {
            throw new IllegalStateException("No tokens available");
        }
        Token currentToken = remainingTokens.iterator().next();

        CellPosition position = new CellPosition(x, y);

        try {
            currentToken.moveTo(position);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid move: " + e.getMessage());
        }

        int moveOrder = moves.size() + 1;
        MoveEntity newMove = new MoveEntity(gameEntity, moveOrder, x, y);
        moveRepository.save(newMove);

//        gameDao.update(game);
        // Déterminer l'ordre du coup
//        int moveOrder = moveCounters.getOrDefault(gameId, 0) + 1;
//        moveCounters.put(gameId, moveOrder);

        // Enregistrer le coup dans la table moves
//        moveDAO.saveMove(gameId, moveOrder, x, y);
        return game;
    }

    /**
     * Retrieves a collection of games associated with a specific player,
     * identified by the provided user ID. This includes games where the
     * player is either the first or second participant.
     *
     * @param userId the unique identifier of the player for whom the games are to be retrieved
     * @return a collection of Game objects that are associated with the provided player ID
     */
    @Override
    public Collection<Game> getGamesByPlayer(String userId) {
        List<GameEntity> gameEntities = gameRepository.findByPlayer1IdOrPlayer2Id(userId, userId);
        List<Game> games = new ArrayList<>();
        for (GameEntity gameEntity : gameEntities) {
            Game game = reconstructGame(gameEntity);
            if (game != null) {
                games.add(game);
            }
        }

        return games;
//        if (gameEntities.isEmpty()) {
//            return Collections.emptyList();
//        }
//        GamePlugin plugin = findPlugin(gameEntities.getGameType());
//        if (plugin == null) {
//            return null;
//        }
//        Game game = plugin.createGame(OptionalInt.of(2), OptionalInt.empty());
//        for (GameEntity gameEntity : gameEntities) {
//
//        }
//        return null;
    }


    //Helper method
    private GamePlugin findPlugin(String gameType) {
        return plugins.get(gameType);
    }

    /**
     * Reconstructs a Game object from a given GameEntity by retrieving the associated game type,
     * creating a new game instance, and applying the moves in the correct order.
     *
     * @param gameEntity the entity representing the state of the game to be reconstructed
     * @return the reconstructed Game object, or null if no matching game plugin is found
     */
    private Game reconstructGame(GameEntity gameEntity) {
        GamePlugin plugin = findPlugin(gameEntity.getGameType());
        if (plugin == null) {
            return null;
        }

        Game game = plugin.createGame(OptionalInt.of(2), OptionalInt.empty());
        List<MoveEntity> moves = moveRepository.findByGameOrderByMoveOrderAsc(gameEntity);

        for(MoveEntity moveEntity : moves) {
            Collection<Token> remainingTokens = game.getRemainingTokens();
            if(!remainingTokens.isEmpty()) {
                Token token = remainingTokens.iterator().next();
                CellPosition position = new CellPosition(moveEntity.getX(), moveEntity.getY());
                try {
                    token.moveTo(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return game;
    }

}
