package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import com.game.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;


@Service
public class PlayerServiceImpl implements PlayerService {
    PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Page<Player> showAllPlayers(String name, String title, Race race, Profession profession, Long after,
                                       Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                       Integer minLevel, Integer maxLevel, Integer pageNumber, Integer pageSize, PlayerOrder order) {
        System.out.println("minExperience = " + minExperience);
        System.out.println("maxExperience = " + maxExperience);
        System.out.println("minLevel = " + minLevel);
        System.out.println("maxLevel = " + maxLevel);
             return playerRepository.findAll(Specification.where(PlayerSpecifications.getPlayersByNameLikeSpec(name)
                                        .and(PlayerSpecifications.getPlayersByTitleLikeSpec(title))
                                        .and(PlayerSpecifications.getPlayersByRiceSpec(race))
                                        .and(PlayerSpecifications.getPlayersByProfessionSpec(profession))
                                        .and(PlayerSpecifications.getPlayersByBirthdayAfterSpec(after))
                                        .and(PlayerSpecifications.getPlayersByBirthdayBeforeSpec(before))
                                        .and(PlayerSpecifications.getPlayersByBannedSpec(banned))
                                        .and(PlayerSpecifications.getPlayersByMinExperienceSpec(minExperience))
                                        .and(PlayerSpecifications.getPlayersByMaxExperienceSpec(maxExperience))
                                        .and(PlayerSpecifications.getPlayersByMinLevelSpec(minLevel))
                                        .and(PlayerSpecifications.getPlayersByMaxLevelSpec(maxLevel))),
                     PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName())));
    }

    public Long countAllPlayers(String name, String title, Race race, Profession profession, Long after,
                                       Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                       Integer minLevel, Integer maxLevel, Integer pageNumber, Integer pageSize, PlayerOrder order) {
        return playerRepository.count(Specification.where(PlayerSpecifications.getPlayersByNameLikeSpec(name)
                                         .and(PlayerSpecifications.getPlayersByTitleLikeSpec(title)))
                                         .and(PlayerSpecifications.getPlayersByRiceSpec(race))
                                         .and(PlayerSpecifications.getPlayersByProfessionSpec(profession))
                                         .and(PlayerSpecifications.getPlayersByBirthdayAfterSpec(after))
                                         .and(PlayerSpecifications.getPlayersByBirthdayBeforeSpec(before))
                                         .and(PlayerSpecifications.getPlayersByBannedSpec(banned))
                                         .and(PlayerSpecifications.getPlayersByMinExperienceSpec(minExperience))
                                         .and(PlayerSpecifications.getPlayersByMaxExperienceSpec(maxExperience))
                                         .and(PlayerSpecifications.getPlayersByMinLevelSpec(minLevel))
                                         .and(PlayerSpecifications.getPlayersByMaxLevelSpec(maxLevel)));
    }
}
