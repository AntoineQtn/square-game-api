package org.example.squaregameapi.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.le_campus_numerique.square_games.engine.Game;
import org.example.squaregameapi.database.DatabaseConnection;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the GameDAO interface for managing persistence of Game entities with a MySQL database.
 * This class is marked as the primary Spring Bean for GameDAO implementations.
 *
 * Methods in this class provide basic CRUD (Create, Read, Update, Delete) operations for Game objects.
 * Currently, the methods are not implemented and need to be connected to a MySQL database.
 *
 * An `ObjectMapper` instance may be leveraged for converting Game objects to and from JSON during database operations.
 * The class is annotated with @Repository to indicate its role in Spring's persistence layer.
 * Additionally, it is annotated with @Primary to denote that it should be prioritized when multiple GameDAO implementations are available.
 */
@Repository
@Primary
public class GameDAOMySQLImpl implements GameDAO  {

    // ObjectMapper to convert Game objects to and from JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void save(Game game) {
        String sql = "INSERT INTO games (id, game_type, game_state) VALUES (?, ?, ?)";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Getting game data
            String id =game.getId().toString();
            String gameType = game.getFactoryId();

            // Converting game data to JSON
            String gameStateJson = objectMapper.writeValueAsString(game);

            stmt.setString(1, id);
            stmt.setString(2, gameType);
            stmt.setString(3, gameStateJson);

            stmt.executeUpdate();
            System.out.println("Game saved successfully : " + id);

        }catch (SQLException e) {
            System.out.println("SQL error while saving game : " + e.getMessage());
        }
    }

    @Override
    public Game findById(String id) {
        return null;
    }

    @Override
    public Collection<Game> findAll() {
        return List.of();
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void delete(String id) {

    }
}
