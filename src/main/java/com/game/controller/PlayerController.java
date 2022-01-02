package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.entity.exception.PlayerBadRequestException;
import com.game.entity.exception.PlayerNotFoundException;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
public class PlayerController {
    PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public static boolean checkId(String id) {
        return Pattern.compile("[0-9]+").matcher(id).matches();
    }

    @GetMapping("/rest/players/count")
    @ResponseBody
    public Long showCountPlayersByConditions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            @RequestParam(defaultValue = "ID") String order,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize

    ) {
        return playerService.countAllPlayers(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, pageNumber, pageSize, PlayerOrder.valueOf(order.toUpperCase()));
    }

    @GetMapping("/rest/players")
    @ResponseBody
    public List<Player> showPlayersByConditions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            @RequestParam(defaultValue = "ID") String order,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize

    ) {
        return playerService.showAllPlayers(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, pageNumber, pageSize, PlayerOrder.valueOf(order.toUpperCase())).getContent();
    }

    @GetMapping("/rest/players/{id}")
    @ResponseBody
    public Player showPlayersById(@PathVariable String id) {
        if ("0".equals(id) || !checkId(id)) throw new PlayerBadRequestException();
        Long idLong = 1l;
        Player p = null;
        try {
            idLong = Long.parseLong(id);
            p = playerService.findPlayerById(idLong).orElseThrow(() -> new PlayerNotFoundException());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return p;
    }
}
