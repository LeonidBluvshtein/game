package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PlayerSpecifications {
    public static Specification<Player> getPlayersByNameLikeSpec(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Player> getPlayersByTitleLikeSpec(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Player> getPlayersByRiceSpec(Race race) {
        return (root, query, criteriaBuilder) -> {
            if (race == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.equal(root.get("race"), race);
        };
    }

    public static Specification<Player> getPlayersByProfessionSpec(Profession profession) {
        return (root, query, criteriaBuilder) -> {
            if (profession == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.equal(root.get("profession"), profession);
        };
    }

    public static Specification<Player> getPlayersByBirthdayAfterSpec(Long after) {
        return (root, query, criteriaBuilder) -> {
            if (after == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.greaterThan(root.get("birthday"), new Date(after));
        };
    }

    public static Specification<Player> getPlayersByBirthdayBeforeSpec(Long before) {
        return (root, query, criteriaBuilder) -> {
            if (before == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.lessThan(root.get("birthday"), new Date(before));
        };
    }

    public static Specification<Player> getPlayersByBannedSpec(Boolean banned) {
        return (root, query, criteriaBuilder) -> {
            if (banned == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.equal(root.get("banned"), banned);
        };
    }

    public static Specification<Player> getPlayersByMinExperienceSpec(Integer minExperience) {
        return (root, query, criteriaBuilder) -> {
            if (minExperience == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.ge(root.get("experience"), minExperience);
        };
    }

    public static Specification<Player> getPlayersByMaxExperienceSpec(Integer maxExperience) {
        return (root, query, criteriaBuilder) -> {
            if (maxExperience == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.le(root.get("experience"), maxExperience);
        };
    }

    public static Specification<Player> getPlayersByMinLevelSpec(Integer minLevel) {
        return (root, query, criteriaBuilder) -> {
            if (minLevel == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.ge(root.get("level"), minLevel);
        };
    }

    public static Specification<Player> getPlayersByMaxLevelSpec(Integer maxLevel) {
        return (root, query, criteriaBuilder) -> {
            if (maxLevel == null) {
                return criteriaBuilder.and();
            }
            return criteriaBuilder.le(root.get("level"), maxLevel);
        };
    }
}
