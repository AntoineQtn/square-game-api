package org.example.squaregameapi.controller;

import org.example.squaregameapi.dto.GameInfo;
import org.example.squaregameapi.plugin.GamePlugin;
import org.example.squaregameapi.service.GameService;
import org.example.squaregameapi.dto.GameCreationParams;
import org.example.squaregameapi.dto.MoveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * REST controller that provides endpoints for managing and interacting with games.
 * The GameController delegates requests to the GameService to handle the core game logic.
 */
@RestController
public class GameController {

    /**
     * Service responsible for processing game-related operations.
     * This instance of GameService is injected into the controller and provides
     * the core functionality for creating games, retrieving game state details, and
     * handling game moves. It enables separation of concerns, delegating the business logic
     * for game management to a dedicated service layer.
     */
    @Autowired
    private GameService gameService;

    @Autowired
    private List<GamePlugin> plugins;

    @GetMapping("/games")
    public Collection<GameInfo> getGames(
            @RequestHeader(name = "Accept-Language", defaultValue = "en") Locale locale) {
        return plugins.stream()
                .map(plugin -> new GameInfo(
                        plugin.getGameType(),
                        plugin.getName(locale)
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/games/{gameId}")
    public Object getGame(@PathVariable String gameId) {
        return gameService.getGame(gameId);
    }

    /**
     * Creates a new game with the specified parameters and returns the unique identifier
     * of the newly created game.
     *
     * @param params the parameters for creating the game, including the game type,
     *               number of players, and board size
     * @return the unique identifier of the newly created game
     */
    @PostMapping("/games")
    public String createGame(@RequestBody GameCreationParams params) {
        String gameId = gameService.createGame(params.getGameType(), params.getPlayerCount(), params.getBoardSize());
        return gameId;
    }

    /**
     * Handles a move operation in the specified game by delegating the request to the game service.
     *
     * @param gameId the unique identifier of the game where the move is to be played
     * @param move the move request object containing the coordinates (x, y) of the move
     * @return the updated state of the game after the move has been processed
     */
    @PatchMapping("/games/{gameId}/move")
    public Object playMove(
            @PathVariable String gameId,
            @RequestBody MoveRequest move
    ) {
        return gameService.playMove(gameId, move.getX(), move.getY());
    }

}
