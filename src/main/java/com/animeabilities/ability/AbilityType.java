package com.animeabilities.ability;

import java.util.Arrays;
import java.util.Optional;

public enum AbilityType {
    SUSANOO("susanoo", "Susanoo", "SUS", AbilityCategory.NARUTO, "Summons a giant spectral warrior with a protective aura and devastating shockwave slashes."),
    SHADOW_CLONE("shadowclone", "Shadow Clone Jutsu", "CLN", AbilityCategory.NARUTO, "Spawns three agile shadow clones that hunt and strike nearby enemies."),
    CHIDORI("chidori", "Chidori", "CHID", AbilityCategory.NARUTO, "Charges concentrated lightning in your hand before dashing forward to pierce enemies."),
    KIRIN("kirin", "Kirin", "KIR", AbilityCategory.NARUTO, "Calls down a thunderous lightning dragon from storm clouds onto your target area."),
    METEOR_DROP("meteor", "Meteor Drop", "MET", AbilityCategory.ORIGINAL, "Pulls a blazing meteor from the sky that crashes and craters the target location."),
    RASENGAN("rasengan", "Rasengan", "RAS", AbilityCategory.NARUTO, "Compresses spiraling chakra into your palm and slams an enemy with grinding force."),
    RASENSHURIKEN("rasenshuriken", "Rasenshuriken", "SHUR", AbilityCategory.NARUTO, "Hurls a high-speed spinning wind shuriken that detonates into a vortex of blades."),
    KAMUI("kamui", "Kamui", "KAM", AbilityCategory.NARUTO, "Phases your body into a dimension of invulnerability before warping onto a target."),
    BANKAI("bankai", "Bankai", "BAN", AbilityCategory.BLEACH, "Unleashes spiritual pressure, granting speed, strength, and slash waves on every swing."),
    GETSUGA_TENSHO("getsuga", "Getsuga Tensho", "GET", AbilityCategory.BLEACH, "Launches a towering crescent wave of condensed spiritual energy."),
    SENBONZAKURA("senbonzakura", "Senbonzakura", "SEN", AbilityCategory.BLEACH, "Scatters thousands of razor-sharp cherry blossom petals that shred enemies around you."),
    MALEVOLENT_SHRINE("malevolentshrine", "Malevolent Shrine", "SHR", AbilityCategory.JUJUTSU_KAISEN, "Manifests a cursed shrine that relentlessly slashes everything within its radius."),
    HOLLOW_PURPLE("hollowpurple", "Hollow Purple", "PUR", AbilityCategory.JUJUTSU_KAISEN, "Fuses Red and Blue into an imaginary mass orb that obliterates everything in its path."),
    DOMAIN_EXPANSION("domainexpansion", "Domain Expansion", "DOM", AbilityCategory.JUJUTSU_KAISEN, "Encloses enemies inside an obsidian barrier dome and executes a guaranteed-hit strike."),
    CLEAVE("cleave", "Cleave", "CLV", AbilityCategory.JUJUTSU_KAISEN, "Delivers a rapid multi-hit web of invisible slashes to all enemies in front of you."),
    FLYING_RAIJIN("flyingraijin", "Flying Raijin", "RAI", AbilityCategory.NARUTO, "Places a spatial seal or teleports instantly to your marked location/target with a slash."),
    GRAVITY_CRUSH("gravitycrush", "Gravity Crush", "GRAV", AbilityCategory.ORIGINAL, "Pulls enemies inward to a gravitational singularity, lifts them, and slams them down."),
    WIND_PRISON("windprison", "Wind Prison", "WIND", AbilityCategory.ORIGINAL, "Traps nearby enemies inside a turbulent cyclone barrier that prevents escape."),
    LIGHTNING_CAGE("lightningcage", "Lightning Cage", "CAGE", AbilityCategory.ORIGINAL, "Snares ground or airborne Elytra enemies in an electrified lattice prison."),
    AERIAL_SUPPRESSION("aerialsuppression", "Aerial Suppression", "AIR", AbilityCategory.ORIGINAL, "Locks onto airborne/Elytra targets, grounds their glide, and spikes them downward."),
    MACE_BREAKER("macebreaker", "Mace Breaker", "MACE", AbilityCategory.ORIGINAL, "Enters a counter-stance and unleashes a shockwave that punishes Mace smashers and rushers."),
    DRAGONS_WRATH("dragonswrath", "Dragon's Wrath", "DRAG", AbilityCategory.ORIGINAL, "Summons a serpentine spectral dragon that weaves through enemies and breathes fire."),
    SOUL_EXTRACTION("soulextraction", "Soul Extraction", "SOUL", AbilityCategory.ORIGINAL, "Rips spiritual energy from a target, weakening them and healing the caster."),
    TIME_STOP("timestop", "Time Stop", "TIME", AbilityCategory.ORIGINAL, "Halts time in an area, freezing nearby enemies in place before shattered-time damage hits.");

    private final String commandName;
    private final String displayName;
    private final String activationCode;
    private final AbilityCategory category;
    private final String description;

    AbilityType(String commandName, String displayName, String activationCode, AbilityCategory category, String description) {
        this.commandName = commandName;
        this.displayName = displayName;
        this.activationCode = activationCode;
        this.category = category;
        this.description = description;
    }

    public String getCommandName() { return commandName; }
    public String getDisplayName() { return displayName; }
    public String getActivationCode() { return activationCode; }
    public AbilityCategory getCategory() { return category; }
    public String getDescription() { return description; }

    public static Optional<AbilityType> fromCommandInput(String input) {
        if (input == null || input.isBlank()) return Optional.empty();
        String normalized = input.trim().toLowerCase().replace("_", "").replace("-", "");
        return Arrays.stream(values())
                .filter(type -> type.commandName.equalsIgnoreCase(normalized)
                        || type.displayName.toLowerCase().replace(" ", "").replace("'", "").equals(normalized)
                        || type.activationCode.equalsIgnoreCase(normalized))
                .findFirst();
    }

    public static Optional<AbilityType> fromChatCode(String message) {
        if (message == null || message.isBlank()) return Optional.empty();
        String trimmed = message.trim();
        return Arrays.stream(values())
                .filter(type -> type.activationCode.equalsIgnoreCase(trimmed))
                .findFirst();
    }
}
