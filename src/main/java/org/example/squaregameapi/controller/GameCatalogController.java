package org.example.squaregameapi.controller;

import org.example.squaregameapi.GameCatalog;
import org.example.squaregameapi.GamePlugin;
import org.example.squaregameapi.dto.GameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
public class GameCatalogController {

    //    @Autowired
//    private GameCatalog gamecatalog;
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


//    @GetMapping("/games")
//    public Collection<String> getGameId() {
//        return this.gamecatalog.getGames();
//    }


}
