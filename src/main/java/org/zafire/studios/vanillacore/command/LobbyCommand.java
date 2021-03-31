package org.zafire.studios.vanillacore.command;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.PlayerCache;

import net.kyori.adventure.text.TextComponent;

public final class LobbyCommand implements CommandExecutor {
    private final VanillaCorePlugin plugin;
    private final Server server;
    private final Logger logger;
    private final PlayerCache playerCache;
    private final MessageParser messageParser;
    private final BukkitScheduler bukkitScheduler;

    public LobbyCommand(final VanillaCorePlugin plugin) {
        this.plugin = plugin;
        server = plugin.getServer();
        logger = plugin.getLogger();
        playerCache = plugin.getPlayerCache();
        messageParser = plugin.getMessageParser();
        bukkitScheduler = server.getScheduler();
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
            final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            final UUID playerUuid = player.getUniqueId();
            playerCache.add(playerUuid);

            bukkitScheduler.runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    playerCache.remove(playerUuid);
                }

            }, 4000L);

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
