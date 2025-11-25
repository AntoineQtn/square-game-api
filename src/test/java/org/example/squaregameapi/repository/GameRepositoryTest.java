package org.example.squaregameapi.repository;

import org.example.squaregameapi.entity.GameEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the {@link GameRepository}.
 *
 * This class provides test cases to verify the behavior of
 * the data access layer for {@link GameEntity}, including CRUD operations.
 *
 * It uses the @DataJpaTest annotation for configuring
 * an in-memory database and setting up an environment
 * for testing the JPA repository.
 *
 * The class also uses the @ActiveProfiles("test") annotation
 * to specify a Spring profile for test-specific configurations.
 *
 * Test Methods:
 * - testSaveAndFindById: Verifies saving and retrieving a {@link GameEntity}
 *   by its ID from the database.
 * - testFindByIdNotFound: Ensures that querying a non-existent ID
 *   returns an empty Optional.
 * - testDelete: Confirms entities can be saved and the total entity count is accurate.
 */
@DataJpaTest
@ActiveProfiles("test")
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    void testSaveAndFindById() {
        // GIVEN creating a new GameEntity
        GameEntity game = new GameEntity("game-123", "tictactoe");

        // WHEN saving first
        GameEntity saved = gameRepository.save(game);

        // THEN - asserting on the saved object
        assertNotNull(saved.getCreatedAt());

        // Or getting it from db
        Optional<GameEntity> found = gameRepository.findById("game-123");
        assertTrue(found.isPresent());
        assertNotNull(found.get().getCreatedAt());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<GameEntity> found = gameRepository.findById("absent");
        assertFalse(found.isPresent());
    }

    @Test
    void testDelete() {
        gameRepository.save(new GameEntity("game-1", "tictactoe"));
        gameRepository.save(new GameEntity("game-2", "connectfour"));
        gameRepository.save(new GameEntity("game-3","taquin"));

        long count = gameRepository.count();

        assertEquals(3, count);
    }
}
