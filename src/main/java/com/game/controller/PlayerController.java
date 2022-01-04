package com.game.controller;

import com.game.entity.*;
import com.game.entity.exception.PlayerBadRequestException;
import com.game.entity.exception.PlayerNotFoundException;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class PlayerController {
    public static final int NAME_LENGTH = 12;
    public static final int TITLE_LENGTH = 30;
    public static final int MIN_EXPERIENCE = 0;
    public static final int MAX_EXPERIENCE = 10_000_000;
    public static final long EARLIEST_DATE = LocalDate.of(2000, 1, 1)
            .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
    public static final long LATEST_DATE = LocalDate.of(3000, 1, 1)
            .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
    PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    private static long checkId(String id) {
        if (!Pattern.compile("[0-9]+").matcher(id).matches()) throw new PlayerBadRequestException();
        long playerId = Long.parseLong(id);
        if (playerId == 0) throw new PlayerBadRequestException();
        return playerId;
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
    public Player showPlayersById(@PathVariable String id) {
        return playerService.findPlayerById(checkId(id)).orElseThrow(PlayerNotFoundException::new);
    }

    @PostMapping("/rest/players")
    public Player createNewPlayer(@RequestBody PlayerCreateDto playerCreateDto) {
        if (playerCreateDto.getName() == null || !checkName(playerCreateDto.getName())) {
            throw new PlayerBadRequestException();
        }
        if (playerCreateDto.getTitle() == null || !checkTitle(playerCreateDto.getTitle())) {
            throw new PlayerBadRequestException();
        }
        if (playerCreateDto.getExperience() == null || !checkExperience(playerCreateDto.getExperience())) {
            throw new PlayerBadRequestException();
        }
        if (playerCreateDto.getBirthday() == null || !checkBirthday(playerCreateDto.getBirthday())) {
            throw new PlayerBadRequestException();
        }
        if (playerCreateDto.getBanned() == null) playerCreateDto.setBanned(false);
        Player p = new Player(playerCreateDto.getName(), playerCreateDto.getTitle(), playerCreateDto.getRace(),
                playerCreateDto.getProfession(), playerCreateDto.getExperience(), new Date(playerCreateDto.getBirthday()),
                playerCreateDto.getBanned());
        return playerService.createOrUpdatePlayer(p);
    }

    @DeleteMapping("/rest/players/{id}")
    @Transactional
    public void deletePlayer(@PathVariable String id) {
        Player player = playerService.findPlayerById(checkId(id)).orElseThrow(PlayerNotFoundException::new);
        playerService.removePlayer(player);
    }

    @PostMapping("/rest/players/{id}")
    @Transactional
    public Player updatePlayer(@PathVariable String id, @RequestBody PlayerUpdateDto playerUpdateDto) {
        Player player = playerService.findPlayerById(checkId(id)).orElseThrow(PlayerNotFoundException::new);
        if (playerUpdateDto.getName() != null) {
            if (checkName(playerUpdateDto.getName())) {
                player.setName(playerUpdateDto.getName());
            } else throw new PlayerBadRequestException();
        }
        if (playerUpdateDto.getTitle() != null) {
            if (checkTitle(playerUpdateDto.getTitle())) {
                player.setTitle(playerUpdateDto.getTitle());
            } else throw new PlayerBadRequestException();
        }
        if (playerUpdateDto.getRace() != null) {
            player.setRace(playerUpdateDto.getRace());
        }
        if (playerUpdateDto.getProfession() != null) {
            player.setProfession(playerUpdateDto.getProfession());
        }
        if (playerUpdateDto.getExperience() != null) {
            if (checkExperience(playerUpdateDto.getExperience())) {
                player.setExperience(playerUpdateDto.getExperience());
            } else throw new PlayerBadRequestException();
        }
        if (playerUpdateDto.getBirthday() != null) {
            if (checkBirthday(playerUpdateDto.getBirthday())) {
                player.setBirthday(new Date(playerUpdateDto.getBirthday()));
            } else throw new PlayerBadRequestException();
        }
        if (playerUpdateDto.getBanned() != null) {
            player.setBanned(playerUpdateDto.getBanned());
        }
        return playerService.createOrUpdatePlayer(player);
    }

    private static boolean checkName(String name) {
        return name.trim().length() > 0 && name.length() <= NAME_LENGTH;
    }

    private static boolean checkTitle(String title) {
        return title.trim().length() > 0 && title.length() <= TITLE_LENGTH;
    }

    private static boolean checkExperience(Integer experience) {
        return experience >= MIN_EXPERIENCE && experience <= MAX_EXPERIENCE;
    }

    private static boolean checkBirthday(Long birthday) {
        return birthday >= EARLIEST_DATE && birthday < LATEST_DATE;
    }
}
