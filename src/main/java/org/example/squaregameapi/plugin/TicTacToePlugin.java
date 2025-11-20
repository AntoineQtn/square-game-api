package org.example.squaregameapi.plugin;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.OptionalInt;

/**
 * A plugin implementation for the Tic-Tac-Toe game that adheres to the {@link GamePlugin} interface.
 * This class provides methods for retrieving the game type, fetching localized game names,
 * and creating game instances with configurable parameters such as player count and board size.
 * The configuration values for player count and board size are dynamically loaded from
 * the application's configuration properties.
 */
@Component
public class TicTacToePlugin implements GamePlugin {

    /**
     * Represents the default number of players for a Tic-Tac-Toe game.
     * This value is dynamically resolved from the application configuration property
     * 'game.tictactoe.default-player-count'.
     */
    @Value("${game.tictactoe.default-player-count}")
    private int defaultPlayerCount;

    @Value("${game.tictactoe.default-board-size}")
    private int defaultBoardSize;

    @Autowired
    private MessageSource messageSource;

    private final TicTacToeGameFactory factory = new TicTacToeGameFactory();

    @Override
    public String getGameType() {
        return "tic-tac-toe";
    }

    @Override
    public String getName(Locale locale) {
        return messageSource.getMessage(
                "game.tictactoe.name",
                null,
                locale
        );
    }

    @Override
    public Game createGame(OptionalInt playerCount, OptionalInt boardSize) {
        // Récupérer les valeurs
        int playerCountValue = playerCount.orElse(defaultPlayerCount);
        int boardSizeValue = boardSize.orElse(defaultBoardSize);

        return factory.createGame(playerCountValue, boardSizeValue);
    }
}
