package org.example.squaregameapi;

import fr.le_campus_numerique.square_games.engine.Game;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Locale;
import java.util.OptionalInt;

public interface GamePlugin {

//    @NotBlank
//    String getName(Locale locale);
//
//    @NotNull
//    Game createGame(
//            OptionalInt playerCount,
//            OptionalInt boardSize
//    );

    String getGameType();

    String getName(Locale locale);

    Game createGame(OptionalInt playerCount, OptionalInt boardSize);
}
