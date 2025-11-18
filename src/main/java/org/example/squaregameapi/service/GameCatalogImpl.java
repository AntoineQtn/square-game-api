package org.example.squaregameapi.service;

import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGame;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.example.squaregameapi.GameCatalog;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class GameCatalogImpl implements GameCatalog {

    private final TicTacToeGameFactory tictactoe = new TicTacToeGameFactory();
    TicTacToeGame ticTacToeGame = (TicTacToeGame) game;

    @Override
    public Collection<String> getGames() {
        return Collections.singleton(this.tictactoe.getGameFactoryId());
    }

}
