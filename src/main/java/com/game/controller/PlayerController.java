package com.game.controller;

import com.game.entity.Player;
import com.game.entity.PlayerCreateDto;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.entity.exception.PlayerBadRequestException;
import com.game.entity.exception.PlayerNotFoundException;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static boolean checkId(String id) {
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
        if (!checkId(id)) throw new PlayerBadRequestException();
        long idLong;
        Player p = null;
        try {
            idLong = Long.parseLong(id);
            if(idLong == 0) throw new PlayerBadRequestException();
            p = playerService.findPlayerById(idLong).orElseThrow(PlayerNotFoundException::new);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return p;
    }

    @PostMapping("/rest/players")
    public Player createNewPlayer(@RequestBody PlayerCreateDto playerCreateDto) {
    if(!checkName(playerCreateDto.getName())) throw new PlayerBadRequestException();
    if(!checkTitle(playerCreateDto.getTitle())) throw new PlayerBadRequestException();
    if(!checkExperience(playerCreateDto.getExperience())) throw new PlayerBadRequestException();
    if(!checkBirthday(playerCreateDto.getBirthday())) throw new PlayerBadRequestException();
    if(playerCreateDto.getBanned() == null) playerCreateDto.setBanned(false);
    Player p = new Player(playerCreateDto.getName(), playerCreateDto.getTitle(), playerCreateDto.getRace(),
            playerCreateDto.getProfession(), playerCreateDto.getExperience(), new Date(playerCreateDto.getBirthday()),
            playerCreateDto.getBanned());
    return playerService.createPlayer(p);
    }

    private static boolean checkName(String name) {
        return name != null && name.trim().length() != 0 && name.length() <= NAME_LENGTH;
    }

    private static boolean checkTitle(String title) {
        return title != null && title.trim().length() != 0 && title.length() <= TITLE_LENGTH;
    }

    private static boolean checkExperience(Integer experience) {
        return experience != null && experience >= MIN_EXPERIENCE && experience <= MAX_EXPERIENCE;
    }

    private static boolean checkBirthday(Long birthday) {
        return birthday != null && birthday >= EARLIEST_DATE && birthday < LATEST_DATE;
    }
}
