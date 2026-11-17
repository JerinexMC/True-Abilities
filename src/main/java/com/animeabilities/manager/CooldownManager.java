package com.animeabilities.manager;

import com.animeabilities.ability.AbilityType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID, Map<AbilityType, Long>> cooldowns = new HashMap<>();

    public boolean isOnCooldown(UUID playerId, AbilityType ability) {
        return getRemainingMillis(playerId, ability) > 0;
    }

    public long getRemainingMillis(UUID playerId, AbilityType ability) {
        Map<AbilityType, Long> playerMap = cooldowns.get(playerId);
        if (playerMap == null) {
            return 0L;
        }
        Long expiry = playerMap.get(ability);
        if (expiry == null) {
            return 0L;
        }
        long remaining = expiry - System.currentTimeMillis();
        if (remaining <= 0L) {
            playerMap.remove(ability);
            return 0L;
        }
        return remaining;
    }

    public double getRemainingSeconds(UUID playerId, AbilityType ability) {
        return Math.ceil(getRemainingMillis(playerId, ability) / 100.0) / 10.0;
    }

    public void setCooldown(UUID playerId, AbilityType ability, double seconds) {
        if (seconds <= 0.0) {
            clearCooldown(playerId, ability);
            return;
        }
        cooldowns.computeIfAbsent(playerId, k -> new EnumMap<>(AbilityType.class))
                .put(ability, System.currentTimeMillis() + (long) (seconds * 1000L));
    }

    public void clearCooldown(UUID playerId, AbilityType ability) {
        Map<AbilityType, Long> playerMap = cooldowns.get(playerId);
        if (playerMap != null) {
            playerMap.remove(ability);
        }
    }

    public void clearAll(UUID playerId) {
        cooldowns.remove(playerId);
    }
}
