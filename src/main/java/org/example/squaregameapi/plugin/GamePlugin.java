package org.example.squaregameapi.plugin;

import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Locale;
import java.util.OptionalInt;

/**
 * Represents a contract for creating and managing game plugins in the system.
 * Implementations of this interface are responsible for defining the specific game type they support,
 * providing localized names for the game, and creating instances of the game with customizable parameters.
 */
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
