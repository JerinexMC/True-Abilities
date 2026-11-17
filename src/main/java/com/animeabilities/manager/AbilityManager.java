package com.animeabilities.manager;

import com.animeabilities.AnimeAbilitiesPlugin;
import com.animeabilities.ability.Ability;
import com.animeabilities.ability.AbilityType;
import com.animeabilities.ability.impl.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class AbilityManager {

    private final AnimeAbilitiesPlugin plugin;
    private final Map<AbilityType, Ability> abilities = new EnumMap<>(AbilityType.class);
    private final Map<UUID, List<BukkitTask>> activeTasks = new HashMap<>();

    public AbilityManager(AnimeAbilitiesPlugin plugin) {
        this.plugin = plugin;
        registerAll();
    }

    private void registerAll() {
        abilities.put(AbilityType.SUSANOO, new SusanooAbility(plugin));
        abilities.put(AbilityType.SHADOW_CLONE, new ShadowCloneAbility(plugin));
        abilities.put(AbilityType.CHIDORI, new ChidoriAbility(plugin));
        abilities.put(AbilityType.KIRIN, new KirinAbility(plugin));
        abilities.put(AbilityType.METEOR_DROP, new MeteorDropAbility(plugin));
        abilities.put(AbilityType.RASENGAN, new RasenganAbility(plugin));
        abilities.put(AbilityType.RASENSHURIKEN, new RasenshurikenAbility(plugin));
        abilities.put(AbilityType.KAMUI, new KamuiAbility(plugin));
        abilities.put(AbilityType.BANKAI, new BankaiAbility(plugin));
        abilities.put(AbilityType.GETSUGA_TENSHO, new GetsugaTenshoAbility(plugin));
        abilities.put(AbilityType.SENBONZAKURA, new SenbonzakuraAbility(plugin));
        abilities.put(AbilityType.MALEVOLENT_SHRINE, new MalevolentShrineAbility(plugin));
        abilities.put(AbilityType.HOLLOW_PURPLE, new HollowPurpleAbility(plugin));
        abilities.put(AbilityType.DOMAIN_EXPANSION, new DomainExpansionAbility(plugin));
        abilities.put(AbilityType.CLEAVE, new CleaveAbility(plugin));
        abilities.put(AbilityType.FLYING_RAIJIN, new FlyingRaijinAbility(plugin));
        abilities.put(AbilityType.GRAVITY_CRUSH, new GravityCrushAbility(plugin));
        abilities.put(AbilityType.WIND_PRISON, new WindPrisonAbility(plugin));
        abilities.put(AbilityType.LIGHTNING_CAGE, new LightningCageAbility(plugin));
        abilities.put(AbilityType.AERIAL_SUPPRESSION, new AerialSuppressionAbility(plugin));
        abilities.put(AbilityType.MACE_BREAKER, new MaceBreakerAbility(plugin));
        abilities.put(AbilityType.DRAGONS_WRATH, new DragonsWrathAbility(plugin));
        abilities.put(AbilityType.SOUL_EXTRACTION, new SoulExtractionAbility(plugin));
        abilities.put(AbilityType.TIME_STOP, new TimeStopAbility(plugin));
    }

    public void reloadConfigurations() {
        // Reloaded via config
    }

    public Collection<Ability> getAllAbilities() {
        return abilities.values();
    }

    public Optional<Ability> getAbility(AbilityType type) {
        return Optional.ofNullable(abilities.get(type));
    }

    public boolean activate(Player player, AbilityType type) {
        Ability ability = abilities.get(type);
        if (ability == null || !ability.isEnabled()) {
            player.sendMessage(plugin.formatMessage("unknown-ability", "input", type.getCommandName()));
            return false;
        }

        if (!plugin.getUnlockManager().hasUnlocked(player, type)) {
            player.sendMessage(plugin.formatMessage("not-unlocked", "ability", type.getDisplayName()));
            return false;
        }

        if (!player.hasPermission("animeabilities.bypass.cooldown")) {
            long remainingMillis = plugin.getCooldownManager().getRemainingMillis(player.getUniqueId(), type);
            if (remainingMillis > 0) {
                double remainingSec = plugin.getCooldownManager().getRemainingSeconds(player.getUniqueId(), type);
                player.sendMessage(plugin.formatMessage("on-cooldown", "ability", type.getDisplayName(), "time", String.valueOf(remainingSec)));
                return false;
            }
        }

        ability.execute(player);
        plugin.getCooldownManager().setCooldown(player.getUniqueId(), type, ability.getCooldown());
        return true;
    }

    public void registerTask(UUID playerId, BukkitTask task) {
        activeTasks.computeIfAbsent(playerId, k -> new ArrayList<>()).add(task);
    }

    public void cancelAllActiveTasks() {
        for (List<BukkitTask> tasks : activeTasks.values()) {
            for (BukkitTask task : tasks) {
                try { task.cancel(); } catch (Exception ignored) {}
            }
        }
        activeTasks.clear();
    }
}
