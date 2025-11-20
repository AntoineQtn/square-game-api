package org.example.squaregameapi.dao;

import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GameDAOMemoryImpl implements GameDAO{

    private final Map<String, Game> games = new HashMap<>();

    @Override
    public void save(Game game) {
        games.put(game.getId().toString(), game);

    }

    @Override
    public Game findById(String id) {
        return games.get(id);
    }

    @Override
    public Collection<Game> findAll() {
        return games.values();
    }

    @Override
    public void update(Game game) {
        games.put(game.getId().toString(), game);

    }

    @Override
    public void delete(String id) {
        games.remove(id);

    }
}
