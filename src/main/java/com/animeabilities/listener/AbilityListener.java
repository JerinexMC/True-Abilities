package com.animeabilities.listener;

import com.animeabilities.AnimeAbilitiesPlugin;
import com.animeabilities.ability.AbilityType;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class AbilityListener implements Listener {

    private final AnimeAbilitiesPlugin plugin;

    public AbilityListener(AnimeAbilitiesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (!plugin.getConfig().getBoolean("settings.chat-activation", true)) return;
        Player player = event.getPlayer();
        String message = PlainTextComponentSerializer.plainText().serialize(event.message()).trim();

        Optional<AbilityType> typeOpt = AbilityType.fromChatCode(message);
        if (typeOpt.isPresent()) {
            event.setCancelled(true);
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                plugin.getAbilityManager().activate(player, typeOpt.get());
            });
        }
    }
}
