package com.animeabilities.ability;

import com.animeabilities.AnimeAbilitiesPlugin;
import com.animeabilities.util.TargetUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public abstract class Ability {

    protected final AnimeAbilitiesPlugin plugin;
    protected final AbilityType type;

    protected Ability(AnimeAbilitiesPlugin plugin, AbilityType type) {
        this.plugin = plugin;
        this.type = type;
    }

    public AbilityType getType() { return type; }

    public boolean isEnabled() {
        return plugin.getConfig().getBoolean("abilities." + type.getCommandName() + ".enabled", true);
    }

    public double getCooldown() {
        return plugin.getConfig().getDouble("abilities." + type.getCommandName() + ".cooldown", 15.0);
    }

    public double getDurationSeconds() {
        return plugin.getConfig().getDouble("abilities." + type.getCommandName() + ".duration", 5.0);
    }

    public int getDurationTicks() {
        return Math.max(1, (int) Math.round(getDurationSeconds() * 20.0));
    }

    public double getDamage() {
        return plugin.getConfig().getDouble("abilities." + type.getCommandName() + ".damage", 10.0);
    }

    public double getRange() {
        return plugin.getConfig().getDouble("abilities." + type.getCommandName() + ".range", 12.0);
    }

    public double getRadius() {
        return plugin.getConfig().getDouble("abilities." + type.getCommandName() + ".radius", 5.0);
    }

    public abstract void execute(Player caster);

    protected void trackTask(UUID casterId, BukkitTask task) {
        plugin.getAbilityManager().registerTask(casterId, task);
    }

    protected void damageTarget(Player caster, LivingEntity target, double amount) {
        if (!TargetUtils.isValidTarget(plugin, caster, target)) return;
        target.setNoDamageTicks(0);
        target.damage(amount, caster);
    }

    protected void runLater(UUID casterId, Runnable runnable, long delayTicks) {
        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, runnable, delayTicks);
        trackTask(casterId, task);
    }
}
