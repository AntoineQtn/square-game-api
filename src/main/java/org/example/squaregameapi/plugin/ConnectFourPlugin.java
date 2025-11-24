package org.example.squaregameapi.plugin;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.OptionalInt;

@Component
public class ConnectFourPlugin implements GamePlugin{

    @Value("${game.connectfour.default-player-count}")
    private int defaultPlayerCount;

    @Value("${game.connectfour.default-board-size}")
    private int defaultBoardSize;

    private final ConnectFourGameFactory factory = new ConnectFourGameFactory();

    @Autowired
    private MessageSource messageSource;


    @Override
    public String getGameType() {
        return "connectfour";
    }

    @Override
    public String getName(Locale locale) {

        return messageSource.getMessage(
                "game.connectfour.name",
                null,
                locale);
    }

    @Override
    public Game createGame(OptionalInt playerCount, OptionalInt boardSize) {
        int playerCountValue = playerCount.orElse(defaultPlayerCount);
        int boardSizeValue = boardSize.orElse(defaultBoardSize);

        return factory.createGame(playerCountValue, boardSizeValue);
    }
}
