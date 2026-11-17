package com.animeabilities.util;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleUtils {
    public static void playAura(Location loc, Particle particle, int count) {
        if (loc.getWorld() != null) {
            loc.getWorld().spawnParticle(particle, loc, count, 0.5, 1.0, 0.5, 0.05);
        }
    }

    public static void playParticleRing(Location center, Particle particle, double radius, int points) {
        if (center.getWorld() == null) return;
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            center.getWorld().spawnParticle(particle, x, center.getY(), z, 1, 0, 0, 0, 0);
        }
    }
}
