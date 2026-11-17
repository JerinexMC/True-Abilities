package com.animeabilities.manager;

import com.animeabilities.AnimeAbilitiesPlugin;
import com.animeabilities.ability.AbilityType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UnlockManager {

    private final AnimeAbilitiesPlugin plugin;
    private final File file;
    private FileConfiguration data;

    public UnlockManager(AnimeAbilitiesPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "unlocks.yml");
        if (!file.exists()) {
            plugin.saveResource("unlocks.yml", false);
        }
        this.data = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        this.data = YamlConfiguration.loadConfiguration(file);
    }

    public boolean hasUnlocked(Player player, AbilityType type) {
        if (!plugin.getConfig().getBoolean("settings.require-unlocks", false)) {
            return true;
        }
        if (player.hasPermission("animeabilities.bypass.unlock")) {
            return true;
        }
        List<String> unlocked = data.getStringList("players." + player.getUniqueId());
        return unlocked.contains(type.getCommandName());
    }

    public void grantAbility(UUID playerId, AbilityType type) {
        String path = "players." + playerId;
        List<String> unlocked = new ArrayList<>(data.getStringList(path));
        if (!unlocked.contains(type.getCommandName())) {
            unlocked.add(type.getCommandName());
            data.set(path, unlocked);
            save();
        }
    }

    public void revokeAbility(UUID playerId, AbilityType type) {
        String path = "players." + playerId;
        List<String> unlocked = new ArrayList<>(data.getStringList(path));
        if (unlocked.remove(type.getCommandName())) {
            data.set(path, unlocked);
            save();
        }
    }

    private void save() {
        try {
            data.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save unlocks.yml: " + e.getMessage());
        }
    }
}
