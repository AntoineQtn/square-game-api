package org.example.squaregameapi.repository;

import org.example.squaregameapi.entity.GameEntity;
import org.example.squaregameapi.entity.MoveEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class MoveRepositoryTest {

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    void testSaveMove(){

        // GIVEN - Creating and saving a GameEntity
        GameEntity game = new GameEntity("game-123", "tictactoe");
        gameRepository.save(game);

        // WHEN - Creating and saving a MoveEntity
        MoveEntity move = new MoveEntity(game, 1, 0, 0);
        MoveEntity saved = moveRepository.save(move);

        // THEN - Vérifier sur l'objet sauvegardé
        assertNotNull(saved.getId());
        assertEquals(1, saved.getMoveOrder());
        assertEquals(0, saved.getX());
        assertEquals(0, saved.getY());

    }

    @Test
    void testFindByGameOrderByMoveOrderAsc() {
        GameEntity game = new GameEntity("game-456", "tictactoe");
        gameRepository.save(game);

        moveRepository.save(new MoveEntity(game, 3, 2, 2));
        moveRepository.save(new MoveEntity(game, 1, 0, 0));
        moveRepository.save(new MoveEntity(game, 2, 1, 1));

        List<MoveEntity> moves = moveRepository.findByGameOrderByMoveOrderAsc(game);

        assertEquals(3, moves.size());
        assertEquals(1, moves.get(0).getMoveOrder());
        assertEquals(2, moves.get(1).getMoveOrder());
        assertEquals(3, moves.get(2).getMoveOrder());
    }

    @Test
    void testFindMovesForDifferentGames() {

        GameEntity game1 = new GameEntity("game-1", "tictactoe");
        GameEntity game2 = new GameEntity("game-2", "connectfour");
        gameRepository.save(game1);
        gameRepository.save(game2);

        moveRepository.save(new MoveEntity(game1, 1, 0, 0));
        moveRepository.save(new MoveEntity(game1, 2, 1, 1));
        moveRepository.save(new MoveEntity(game2, 1, 0, 0));

        List<MoveEntity> movesGame1 = moveRepository.findByGameOrderByMoveOrderAsc(game1);

        assertEquals(2, movesGame1.size());
        assertEquals("game-1", movesGame1.get(0).getGame().getId());
    }

}
