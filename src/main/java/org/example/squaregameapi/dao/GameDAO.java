package org.example.squaregameapi.dao;

import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Collection;

public interface GameDAO {

    void save(Game game);

    Game findById(String id);

    Collection<Game> findAll();

    void update(Game game);

    void delete(String id);

}
