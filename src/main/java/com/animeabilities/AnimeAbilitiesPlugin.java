package com.animeabilities;

import com.animeabilities.command.AbilityCommand;
import com.animeabilities.listener.AbilityListener;
import com.animeabilities.manager.AbilityManager;
import com.animeabilities.manager.BlockRestoreManager;
import com.animeabilities.manager.CombatMechanicsManager;
import com.animeabilities.manager.CooldownManager;
import com.animeabilities.manager.EntityCleanupManager;
import com.animeabilities.manager.UnlockManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnimeAbilitiesPlugin extends JavaPlugin {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private CooldownManager cooldownManager;
    private BlockRestoreManager blockRestoreManager;
    private EntityCleanupManager entityCleanupManager;
    private CombatMechanicsManager combatMechanicsManager;
    private UnlockManager unlockManager;
    private AbilityManager abilityManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.cooldownManager = new CooldownManager();
        this.blockRestoreManager = new BlockRestoreManager(this);
        this.entityCleanupManager = new EntityCleanupManager();
        this.combatMechanicsManager = new CombatMechanicsManager(this);
        this.unlockManager = new UnlockManager(this);
        this.abilityManager = new AbilityManager(this);

        AbilityCommand commandExecutor = new AbilityCommand(this);
        PluginCommand cmd = getCommand("ability");
        if (cmd != null) {
            cmd.setExecutor(commandExecutor);
            cmd.setTabCompleter(commandExecutor);
        }

        getServer().getPluginManager().registerEvents(new AbilityListener(this), this);
        getLogger().info("AnimeAbilities enabled with " + abilityManager.getAllAbilities().size() + " abilities.");
    }

    @Override
    public void onDisable() {
        if (abilityManager != null) {
            abilityManager.cancelAllActiveTasks();
        }
        if (combatMechanicsManager != null) {
            combatMechanicsManager.clearAll();
        }
        if (entityCleanupManager != null) {
            entityCleanupManager.cleanupAll();
        }
        if (blockRestoreManager != null) {
            blockRestoreManager.restoreAllImmediately();
        }
        getLogger().info("AnimeAbilities disabled and all world states/entities restored.");
    }

    public void reloadPlugin() {
        reloadConfig();
        unlockManager.reload();
        abilityManager.reloadConfigurations();
    }

    public Component formatMessage(String key, String... placeholders) {
        String prefix = getConfig().getString("messages.prefix", "");
        String raw = getConfig().getString("messages." + key, "");
        for (int i = 0; i + 1 < placeholders.length; i += 2) {
            raw = raw.replace("{" + placeholders[i] + "}", placeholders[i + 1]);
        }
        return MINI_MESSAGE.deserialize(prefix + raw);
    }

    public Component parseMiniMessage(String input) {
        return MINI_MESSAGE.deserialize(input);
    }

    public CooldownManager getCooldownManager() { return cooldownManager; }
    public BlockRestoreManager getBlockRestoreManager() { return blockRestoreManager; }
    public EntityCleanupManager getEntityCleanupManager() { return entityCleanupManager; }
    public CombatMechanicsManager getCombatMechanicsManager() { return combatMechanicsManager; }
    public UnlockManager getUnlockManager() { return unlockManager; }
    public AbilityManager getAbilityManager() { return abilityManager; }
}
