package com.animeabilities.util;

import com.animeabilities.AnimeAbilitiesPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public class TargetUtils {
    public static boolean isValidTarget(AnimeAbilitiesPlugin plugin, Player caster, LivingEntity target) {
        if (target.equals(caster)) return false;
        if (target instanceof Player p) {
            if (p.isInvulnerable() || p.getGameMode() == org.bukkit.GameMode.SPECTATOR) return false;
        }
        return true;
    }

    public static Collection<LivingEntity> getNearbyTargets(Player caster, double radius) {
        return caster.getNearbyEntities(radius, radius, radius).stream()
                .filter(e -> e instanceof LivingEntity)
                .map(e -> (LivingEntity) e)
                .collect(Collectors.toList());
    }
}
