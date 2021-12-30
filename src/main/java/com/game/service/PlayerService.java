package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlayerService {
 /*   List<Player> showAllPlayers(String name, String title, Race race, Profession profession, Long after, Long before,
                                Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                                Integer maxLevel, Integer pageNumber, Integer pageSize);
*/


  Page<Player> showAllPlayers(String name, String title, Race race, Profession profession, Long after, Long before,
                               Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                               Integer maxLevel, Integer pageNumber, Integer pageSize, PlayerOrder order);

    Long countAllPlayers(String name, String title, Race race, Profession profession, Long after, Long before,
                                Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                                Integer maxLevel, Integer pageNumber, Integer pageSize, PlayerOrder order);
}
