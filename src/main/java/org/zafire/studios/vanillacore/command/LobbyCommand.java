package org.zafire.studios.vanillacore.command;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.cache.GeneralCache;

import net.kyori.adventure.text.TextComponent;

public final class LobbyCommand implements CommandExecutor {

    private final VanillaCorePlugin plugin;
    private final Logger logger;
    private final GeneralCache<UUID> playerCache;
    private final MessageParser messageParser;
    private final BukkitScheduler bukkitScheduler;

    public LobbyCommand(final VanillaCorePlugin plugin, final Logger logger, final GeneralCache<UUID> playerCache,
            final MessageParser messageParser, final BukkitScheduler bukkitScheduler) {
        this.plugin = plugin;
        this.logger = logger;
        this.playerCache = playerCache;
        this.messageParser = messageParser;
        this.bukkitScheduler = bukkitScheduler;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
            final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            final UUID playerUuid = player.getUniqueId();
            playerCache.add(playerUuid);

            bukkitScheduler.runTaskLater(plugin, () -> playerCache.remove(playerUuid), 4000L);

            player.saveData();

            final TextComponent success = messageParser
                    .parse("&5&lZafire &8|| &7Teletransportándote al &6Lobby&7, por favor, espera.", player);
            player.sendMessage(success);

            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            try {
                dataOutputStream.writeUTF("Connect");
                dataOutputStream.writeUTF("Lobby");
            } catch (final IOException e) {
                logger.severe("Could not send " + player.getName() + " to the Lobby server!");
            }

            player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
            return true;
        }

        return false;
    }
}
