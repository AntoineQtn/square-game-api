package org.example.squaregameapi.controller;

import fr.le_campus_numerique.square_games.engine.Game;
import org.example.squaregameapi.client.UserClientService;
import org.example.squaregameapi.dto.GameInfo;
import org.example.squaregameapi.plugin.GamePlugin;
import org.example.squaregameapi.service.GameService;
import org.example.squaregameapi.dto.GameCreationParams;
import org.example.squaregameapi.dto.MoveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
public class GameController {

    @Autowired
    private UserClientService userClientService;

    @Autowired
    private GameService gameService;

    @Autowired
    private List<GamePlugin> plugins;

    /**
     * Récupère la liste des types de jeux disponibles.
     */
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

    /**
     * Récupère l'état complet d'une partie.
     */
    @GetMapping("/games/{gameId}")
    public Object getGame(@PathVariable String gameId) {
        return gameService.getGame(gameId);
    }

    @GetMapping("/games/my-games")
    public Collection<Game> getMyGames(@RequestHeader("X-UserId") String userId) {
        return gameService.getGamesByPlayer(userId);
    }

    /**
     * Crée une nouvelle partie et retourne son état complet.
     *
     * @param params paramètres de création (type, nombre de joueurs, taille)
     * @return l'état complet de la partie créée (JSON)
     */
    @PostMapping("/games")
    public Object createGame(
            @RequestBody GameCreationParams params,
            @RequestHeader ("X-UserId") String userId
    ) {
        if(!userClientService.verifyUserExists(userId)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println("Received request: " + params.getGameType());

        String gameId = gameService.createGame(
                params.getGameType(),
                params.getPlayerCount(),
                params.getBoardSize(),
                userId
        );

        System.out.println("Game created with ID: " + gameId);

        Object game = gameService.getGame(gameId);

        System.out.println("Game object: " + game);
        System.out.println("Game is null? " + (game == null));

        return gameService.getGame(gameId);
    }

    /**
     * Joue un coup dans une partie existante.
     *
     * @param gameId identifiant unique de la partie
     * @param move coordonnées du coup (x, y)
     * @return l'état mis à jour de la partie
     */
    @PatchMapping("/games/{gameId}/move")
    public Object playMove(
            @PathVariable String gameId,
            @RequestBody MoveRequest move,
            @RequestHeader("X-UserId") String userId
    ) {
        // Vérifier que l'utilisateur existe
        if (!userClientService.verifyUserExists(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Jouer le coup
        return gameService.playMove(gameId, move.getX(), move.getY(), userId);
    }
}