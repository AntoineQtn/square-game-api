package org.example.squaregameapi.controller;

import org.example.squaregameapi.GameService;
import org.example.squaregameapi.dto.GameCreationParams;
import org.example.squaregameapi.dto.MoveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/games")
    public String createGame(@RequestBody GameCreationParams params) {
        String gameId = gameService.createGame(params.getGameType(), params.getPlayerCount(), params.getBoardSize());
        return gameId;
    }

    @PatchMapping("/games/{gameId}/move")
    public Object playMove(
            @PathVariable String gameId,
            @RequestBody MoveRequest move
    ) {
        return gameService.playMove(gameId, move.getX(), move.getY());
    }


    @GetMapping("/games/{gameId}")
    public Object getGame(@PathVariable String gameId) {
        return gameService.getGame(gameId);
    }

}
