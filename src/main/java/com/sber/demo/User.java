package com.sber.demo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.Map;

@Schema(description = "Пользователь")
public class User {
    private String identifier;
    private String name;
    private String surname;
    @Schema(description = "rating = {\"rating\": int, \"toxicRating\": int}")
    private Map<String, Integer> rating;
    private boolean is_online;
    private String[] skills;

    public User(String identifier, String name, String surname, Map<String, Integer> rating, boolean is_online, String[] skills) {
        this.identifier = identifier;
        this.name = name;
        this.surname = surname;
        this.rating = new HashMap<String, Integer>() {{
            put("rating", 0);
            put("toxicRating", 0);
        }};
        this.is_online = is_online;
        this.skills = skills;
    }
    public User() {
        this.rating = new HashMap<String, Integer>() {{
            put("rating", 0);
            put("toxicRating", 0);
        }};
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Map<String, Integer> getRating() {
        return rating;
    }

    public void setRating(Map<String, Integer> rating) {
        this.rating = rating;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
