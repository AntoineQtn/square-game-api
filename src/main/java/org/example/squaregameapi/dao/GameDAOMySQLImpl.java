package org.example.squaregameapi.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.Token;
import org.example.squaregameapi.database.DatabaseConnection;
import org.example.squaregameapi.dto.MoveDTO;
import org.example.squaregameapi.plugin.GamePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;

/**
 * Implementation of the GameDAO interface for managing persistence of Game entities with a MySQL database.
 * This class is marked as the primary Spring Bean for GameDAO implementations.
 * <p>
 * Methods in this class provide basic CRUD (Create, Read, Update, Delete) operations for Game objects.
 * Currently, the methods are not implemented and need to be connected to a MySQL database.
 * <p>
 * An `ObjectMapper` instance may be leveraged for converting Game objects to and from JSON during database operations.
 * The class is annotated with @Repository to indicate its role in Spring's persistence layer.
 * Additionally, it is annotated with @Primary to denote that it should be prioritized when multiple GameDAO implementations are available.
 */
//@Repository
//@Primary
public class GameDAOMySQLImpl implements GameDAO {

    // ObjectMapper to convert Game objects to and from JSON
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new Jdk8Module());


//    @Autowired
    private MoveDAO moveDAO;

 //   @Autowired
    private List<GamePlugin> plugins;

    /**
     * Saves the provided game instance to the database. The method inserts
     * a new record into the "games" table using the unique game ID and game type.
     *
     * @param game the game object to be saved, which includes game-specific data
     *             such as its unique ID and type
     */
    @Override
    public void save(Game game) {
        String sql = "INSERT INTO games (id, game_type) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String id = game.getId().toString();
            String gameType = game.getFactoryId();

            stmt.setString(1, id);
            stmt.setString(2, gameType);

            stmt.executeUpdate();

            System.out.println("Game saved: " + id + " (type: " + gameType + ")");

        } catch (SQLException e) {
            System.err.println("SQL error while saving game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Finds a game plugin that corresponds to the specified game type.
     *
     * @param gameType the type of the game to search for
     * @return the {@code GamePlugin} corresponding to the given game type, or {@code null} if none is found
     */
    private GamePlugin findPlugin(String gameType) {
        return plugins.stream()
                .filter(p -> p.getGameType().equals(gameType))
                .findFirst()
                .orElse(null);
    }


    @Override
    public Game findById(String id) {
        String sql = "SELECT game_type FROM games WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String gameType = rs.getString("game_type");

                System.out.println(" Game found: " + id + " (type: " + gameType + ")");

                // Finding the corresponding plugin
                GamePlugin plugin = findPlugin(gameType);
                if (plugin == null) {
                    System.err.println(" No plugin found for type: " + gameType);
                    return null;
                }

                // Creating a new empty game instance
                Game game = plugin.createGame(OptionalInt.of(2), OptionalInt.empty());

                // Getting all moves from the db
                List<MoveDTO> moves = moveDAO.findMovesByGameId(id);

                // Playing all moves in the right order
                for (MoveDTO move : moves) {
                    Collection<Token> remainingTokens = game.getRemainingTokens();

                    if (remainingTokens.isEmpty()) {
                        System.err.println(" No more tokens to play at move " + move.getMoveOrder());
                        break;
                    }

                    Token token = remainingTokens.iterator().next();
                    CellPosition position = new CellPosition(move.getX(), move.getY());

                    try {
                        token.moveTo(position);
                    } catch (Exception e) {
                        System.err.println(" Error replaying move " + move.getMoveOrder() + ": " + e.getMessage());
                    }
                }

                System.out.println(" Game reconstructed with " + moves.size() + " moves");

                return game;
            }

            return null;

        } catch (SQLException e) {
            System.err.println(" SQL error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all game instances from the database or data source.
     *
     * @return a collection of {@code Game} objects representing all games
     *         stored in the system. If there are no games, an empty collection is returned.
     */
    @Override
    public Collection<Game> findAll() {
        return new ArrayList<>();
    }

    /**
     * Updates the specified game in the database. This method modifies an existing record
     * in the "games" table by updating the game state and type based on the unique identifier.
     * The game data is serialized into a JSON string before being stored in the database.
     *
     * @param game the game object to be updated, containing the unique ID, game type,
     *             and the current state of the game
     */
    @Override
    public void update(Game game) {
        String sql = "UPDATE games SET game_state = ?, game_type = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String id = game.getId().toString();
            String gameType = game.getFactoryId();
            String gameStateJson = objectMapper.writeValueAsString(game);

            stmt.setString(1, gameStateJson);
            stmt.setString(2, gameType);
            stmt.setString(3, id);

            stmt.executeUpdate();

            System.out.println("Game updated successfully : " + id);
        } catch (SQLException e) {
            System.err.println("SQL error while updating game : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("JSON serialization error while updating game : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {

        String sql = "DELETE FROM games WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();

            System.out.println("Game deleted successfully : " + id);
        } catch (SQLException e) {
            System.err.println("SQL error while deleting game : " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Determines the class of the game based on the provided game type.
     *
     * @param gameType the type of the game as a string. Supported game types include "tictactoe", "connectfour", and "taquin".
     * @return the {@code Class<?>} representing the corresponding game class.
     *         If the game type is unrecognized, returns {@code null}.
     */
    private Class<?> getGameClass(String gameType) {
        switch (gameType) {
            case "tictactoe":
                return fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGame.class;
            case "connectfour":
                return fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGame.class;
            case "taquin":
                return fr.le_campus_numerique.square_games.engine.taquin.TaquinGame.class;
            default:
                System.err.println("Unknown game type: " + gameType);
                return null;
        }
    }

}
