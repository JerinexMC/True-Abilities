package com.animeabilities.ability.impl;

import com.animeabilities.AnimeAbilitiesPlugin;
import com.animeabilities.ability.Ability;
import com.animeabilities.ability.AbilityType;
import com.animeabilities.util.ParticleUtils;
import com.animeabilities.util.TargetUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WindPrisonAbility extends Ability {

    public WindPrisonAbility(AnimeAbilitiesPlugin plugin) {
        super(plugin, AbilityType.WIND_PRISON);
    }

    @Override
    public void execute(Player caster) {
        caster.sendMessage(plugin.parseMiniMessage("<green>Activated Wind Prison!</green>"));
        caster.getWorld().playSound(caster.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.8f, 1.2f);
        ParticleUtils.playAura(caster.getLocation(), org.bukkit.Particle.SOUL_FIRE_FLAME, 20);

        double dmg = getDamage();
        double rng = getRange();

        new BukkitRunnable() {
            int ticks = 0;
            final int maxTicks = getDurationTicks();

            @Override
            public void run() {
                if (!caster.isOnline() || caster.isDead() || ticks++ >= maxTicks) {
                    cancel();
                    return;
                }
                Location loc = caster.getLocation();
                ParticleUtils.playParticleRing(loc.add(0, 1, 0), org.bukkit.Particle.PORTAL, 2.0, 16);
                for (LivingEntity target : TargetUtils.getNearbyTargets(caster, rng)) {
                    damageTarget(caster, target, dmg / (maxTicks / 5.0));
                }
            }
        }.runTaskTimer(plugin, 0L, 5L);
    }
}
