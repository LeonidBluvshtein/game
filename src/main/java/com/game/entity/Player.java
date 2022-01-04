package com.game.entity;


import javax.persistence.*;
import java.util.Date;

@Entity(name= "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String title;
    @Enumerated(EnumType.STRING)
    private Race race;
    @Enumerated(EnumType.STRING)
    private Profession profession;
    private Integer experience; // Диапазон значений 0..10,000,000
    private Integer level;
    private Integer untilNextLevel;
    private Date birthday;  // Диапазон значений года 2000..3000 включительно
    private Boolean banned;

    public Player() {
    }

    public Player(String name, String title, Race race, Profession profession, Integer experience,
                  Date birthday, Boolean banned) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.level = calculateLevel();
        this.untilNextLevel = calculateUntilNextLevel();
        this.birthday = birthday;
        this.banned = banned;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Race getRace() {
        return race;
    }

    public Profession getProfession() {
        return profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
        this.level = calculateLevel();
        this.untilNextLevel = calculateUntilNextLevel();
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    private Integer calculateLevel() {
        return (int) ((Math.sqrt(2500 + 200 * getExperience()) - 50) / 100);
    }

    private Integer calculateUntilNextLevel() {
        return 50 * (getLevel() + 1) * (getLevel() + 2) - getExperience();
    }
}
