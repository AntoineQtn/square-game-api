package org.example.squaregameapi.controller;

import org.example.squaregameapi.GameCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class GameCatalogController {

    @Autowired
    private GameCatalog gamecatalog;


    @GetMapping("/games")
    public Collection<String> getGameId() {
        return this.gamecatalog.getGames();
    }


}
