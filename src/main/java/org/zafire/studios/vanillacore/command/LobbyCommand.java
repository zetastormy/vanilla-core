package org.zafire.studios.vanillacore.command;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

import net.kyori.adventure.text.TextComponent;

public final class LobbyCommand implements CommandExecutor {

    private final VanillaCorePlugin plugin;
    private final GenericCache<UUID> uuidCache;
    private final MessageParser messageParser;
    private final BukkitScheduler scheduler;

    public LobbyCommand(final VanillaCorePlugin plugin, final GenericCache<UUID> uuidCache,
            final MessageParser messageParser, final BukkitScheduler scheduler) {
        this.plugin = plugin;
        this.uuidCache = uuidCache;
        this.messageParser = messageParser;
        this.scheduler = scheduler;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
            final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            final UUID playerUuid = player.getUniqueId();
            uuidCache.add(playerUuid);

            scheduler.runTaskLater(plugin, () -> uuidCache.remove(playerUuid), 4000L);

            player.saveData();

            final TextComponent successMessage = messageParser
                    .parse("&5&lZafire &8|| &7Teletransportándote al &6Lobby&7, por favor, espera.", player);
            player.sendMessage(successMessage);

            final ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
            final DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

            try {
                dataOutput.writeUTF("Connect");
                dataOutput.writeUTF("Lobby");
            } catch (final IOException e) {
                e.printStackTrace();
            }

            player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutput.toByteArray());
            return true;
        }

        return false;
    }
}
