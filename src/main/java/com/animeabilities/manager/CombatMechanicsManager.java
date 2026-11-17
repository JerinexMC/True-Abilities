package com.animeabilities.manager;

import com.animeabilities.AnimeAbilitiesPlugin;
import org.bukkit.entity.Player;

public class CombatMechanicsManager {
    private final AnimeAbilitiesPlugin plugin;
    public CombatMechanicsManager(AnimeAbilitiesPlugin plugin) { this.plugin = plugin; }
    public boolean isUsingElytra(Player player) { return player.isGliding(); }
    public boolean isHoldingMace(Player player) { return player.getInventory().getItemInMainHand().getType().name().contains("MACE"); }
    public void clearAll() {}
}
