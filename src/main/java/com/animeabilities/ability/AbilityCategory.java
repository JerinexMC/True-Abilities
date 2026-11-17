package com.animeabilities.ability;

public enum AbilityCategory {
    NARUTO("Naruto"),
    BLEACH("Bleach"),
    JUJUTSU_KAISEN("Jujutsu Kaisen"),
    ORIGINAL("Original");

    private final String displayName;

    AbilityCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
