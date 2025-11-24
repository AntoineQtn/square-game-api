package org.example.squaregameapi.dao;

import org.example.squaregameapi.dto.MoveDTO;
import java.util.List;

/**
 * Interface for managing persistence operations related to game moves.
 * This DAO (Data Access Object) provides methods for saving, retrieving,
 * and deleting moves associated with specific games.
 */
public interface MoveDAO {

    /**
     * Saves a move for a specific game by storing its details such as order and position.
     *
     * @param gameId The unique identifier for the game to which the move belongs.
     * @param moveOrder The sequential order number of the move in the game's history.
     * @param x The x-coordinate of the move on the game board.
     * @param y The y-coordinate of the move on the game board.
     */
    void saveMove(String gameId, int moveOrder, int x, int y);

    /**
     * Retrieves a list of moves associated with the specified game ID.
     * Each move contains its order and coordinates on the game board.
     *
     * @param gameId The unique identifier of the game whose moves are to be retrieved.
     * @return A list of MoveDTO objects representing the moves of the specified game,
     *         ordered sequentially by move order. Returns an empty list if no moves are found.
     */
    List<MoveDTO> findMovesByGameId(String gameId);

    /**
     * Deletes all moves associated with the specified game ID from the data source.
     *
     * @param gameId The unique identifier of the game whose moves are to be deleted.
     */
    void deleteMovesByGameId(String gameId);
}