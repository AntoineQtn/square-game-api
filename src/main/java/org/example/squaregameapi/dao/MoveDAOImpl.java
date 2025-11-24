package org.example.squaregameapi.dao;

import org.example.squaregameapi.database.DatabaseConnection;
import org.example.squaregameapi.dto.MoveDTO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MoveDAOImpl is an implementation of the MoveDAO interface, responsible for managing
 * persistence operations related to game moves. This includes saving, retrieving, and
 * deleting moves for specific games in the database.
 *
 * This implementation uses JDBC for database communications and relies on the
 * DatabaseConnection utility class to establish JDBC connections.
 *
 * Methods in this class include:
 * - saveMove: Persists move data for a specific game.
 * - findMovesByGameId: Retrieves a list of moves for a given game in sequential order.
 * - deleteMovesByGameId: Deletes all moves associated with a specific game.
 *
 * Exceptions such as SQLExceptions are caught and logged to provide feedback in case
 * of database-related errors.
 *
 * This class uses standard JDBC objects such as PreparedStatement and ResultSet for data manipulation.
 * Connections are managed through try-with-resources to ensure proper resource handling.
 *
 * This class is annotated with @Repository to allow Spring to detect it as a component
 * for persistence operations within the application context.
 */
@Repository
public class MoveDAOImpl implements MoveDAO {

    /**
     * Saves a move for a specified game by inserting its details into the data source.
     *
     * @param gameId The unique identifier of the game to which the move belongs.
     * @param moveOrder The sequential order number of the move in the game's history.
     * @param x The x-coordinate of the move on the game board.
     * @param y The y-coordinate of the move on the game board.
     */
    @Override
    public void saveMove(String gameId, int moveOrder, int x, int y) {
        String sql = "INSERT INTO moves (game_id, move_order, x, y) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gameId);
            stmt.setInt(2, moveOrder);
            stmt.setInt(3, x);
            stmt.setInt(4, y);

            stmt.executeUpdate();

            System.out.println(" Move saved: order=" + moveOrder + " pos=(" + x + ", " + y + ") for game " + gameId);

        } catch (SQLException e) {
            System.err.println(" SQL error while saving move: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of moves associated with the specified game ID from the database.
     * Each move includes its sequential order and coordinates on the game board.
     *
     * @param gameId The unique identifier of the game whose moves are to be retrieved.
     * @return A list of MoveDTO objects representing the moves of the specified game,
     *         ordered in ascending order by move sequence. Returns an empty list if no moves are found.
     */
    @Override
    public List<MoveDTO> findMovesByGameId(String gameId) {
        String sql = "SELECT move_order, x, y FROM moves WHERE game_id = ? ORDER BY move_order ASC";
        List<MoveDTO> moves = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gameId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MoveDTO move = new MoveDTO(
                        rs.getInt("move_order"),
                        rs.getInt("x"),
                        rs.getInt("y")
                );
                moves.add(move);
            }

            System.out.println(" Found " + moves.size() + " moves for game " + gameId);

        } catch (SQLException e) {
            System.err.println(" SQL error while fetching moves: " + e.getMessage());
            e.printStackTrace();
        }

        return moves;
    }

    /**
     * Deletes all moves associated with the specified game ID from the database.
     * This operation removes all records in the "moves" table that match the given game identifier.
     *
     * @param gameId The unique identifier of the game whose associated moves are to be deleted.
     */
    @Override
    public void deleteMovesByGameId(String gameId) {
        String sql = "DELETE FROM moves WHERE game_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gameId);
            stmt.executeUpdate();

            System.out.println(" Moves deleted for game " + gameId);

        } catch (SQLException e) {
            System.err.println(" SQL error while deleting moves: " + e.getMessage());
            e.printStackTrace();
        }
    }
}