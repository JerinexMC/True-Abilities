package com.animeabilities.command;

import com.animeabilities.AnimeAbilitiesPlugin;
import com.animeabilities.ability.AbilityType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AbilityCommand implements CommandExecutor, TabCompleter {

    private final AnimeAbilitiesPlugin plugin;

    public AbilityCommand(AnimeAbilitiesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("animeabilities.use")) {
            sender.sendMessage(plugin.formatMessage("no-permission"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.parseMiniMessage("<yellow>Usage: /ability <ability|list|info|reload></yellow>"));
            return true;
        }

        String sub = args[0].toLowerCase();
        if (sub.equals("list")) {
            sender.sendMessage(plugin.parseMiniMessage("<gold>--- Available Abilities ---</gold>"));
            for (AbilityType type : AbilityType.values()) {
                sender.sendMessage(plugin.parseMiniMessage("<yellow>" + type.getDisplayName() + " (" + type.getActivationCode() + ")</yellow> - " + type.getDescription()));
            }
            return true;
        }

        if (sub.equals("reload")) {
            if (!sender.hasPermission("animeabilities.admin")) {
                sender.sendMessage(plugin.formatMessage("no-permission"));
                return true;
            }
            plugin.reloadPlugin();
            sender.sendMessage(plugin.formatMessage("reloaded"));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can execute abilities.");
            return true;
        }

        Optional<AbilityType> typeOpt = AbilityType.fromCommandInput(args[0]);
        if (typeOpt.isPresent()) {
            plugin.getAbilityManager().activate(player, typeOpt.get());
        } else {
            player.sendMessage(plugin.formatMessage("unknown-ability", "input", args[0]));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("list");
            completions.add("reload");
            for (AbilityType type : AbilityType.values()) {
                completions.add(type.getCommandName());
            }
        }
        return completions.stream().filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).collect(Collectors.toList());
    }
}
