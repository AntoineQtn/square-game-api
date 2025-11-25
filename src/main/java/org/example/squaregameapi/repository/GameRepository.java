package org.example.squaregameapi.repository;

import org.example.squaregameapi.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * GameRepository is a Spring Data JPA repository for managing persistence
 * and CRUD operations on {@link GameEntity}.
 *
 * It provides methods to interact with the "games" table in the database
 * and facilitates operations like saving, updating, deleting, and retrieving
 * GameEntity instances.
 *
 * This interface extends {@link JpaRepository},
 * which provides built-in support for common database operations, such as
 * pagination and sorting, using the entity's ID as the unique identifier.
 */
public interface GameRepository extends JpaRepository<GameEntity, String> {
}
