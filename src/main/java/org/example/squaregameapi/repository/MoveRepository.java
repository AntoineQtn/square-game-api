package org.example.squaregameapi.repository;

import org.example.squaregameapi.entity.GameEntity;
import org.example.squaregameapi.entity.MoveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on the MoveEntity table.
 *
 * This interface extends the JpaRepository, inheriting a variety of methods for creating,
 * updating, reading, and deleting entities of type MoveEntity.
 *
 * Methods:
 * - findByGameOrderByMoveOrderAsc(GameEntity): Retrieves a list of moves associated with
 *   a specific game, ordered in ascending order by move sequence.
 *
 * Entity mappings:
 * - MoveEntity: Represents a move in a game, including its sequence order, coordinates,
 *   and associated game.
 */
public interface MoveRepository extends JpaRepository<MoveEntity, Long> {

    /**
     * Retrieves a list of moves associated with a specific game, ordered in ascending order by the move sequence.
     *
     * @param game the GameEntity instance representing the game for which moves should be retrieved
     * @return a list of MoveEntity instances associated with the given game, sorted by move order in ascending order
     */
    List<MoveEntity> findByGameOrderByMoveOrderAsc(GameEntity game);
}
