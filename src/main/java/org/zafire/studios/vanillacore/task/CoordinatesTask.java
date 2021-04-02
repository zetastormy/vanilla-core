package org.zafire.studios.vanillacore.task;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.MessageParser;

import net.kyori.adventure.text.TextComponent;

public final class CoordinatesTask {

    private final VanillaCorePlugin plugin;
    private final Server server;
    private final BukkitScheduler bukkitScheduler;
    private final MessageParser messageParser;

    public CoordinatesTask(final VanillaCorePlugin plugin) {
        this.plugin = plugin;
        server = plugin.getServer();
        bukkitScheduler = server.getScheduler();
        messageParser = plugin.getMessageParser();
        schedule();
    }

    public void schedule() {
        bukkitScheduler.runTaskTimerAsynchronously(plugin, () -> {
            sendCoordinates();
        }, 300L, 40L);
    }

    private void sendCoordinates() {
        for (Player player : server.getOnlinePlayers()) {
            Location playerLocation = player.getLocation();

            StringBuilder coordinates = new StringBuilder();
            coordinates.append(playerLocation.getX());
            coordinates.append(' ');
            coordinates.append(playerLocation.getY());
            coordinates.append(' ');
            coordinates.append(playerLocation.getZ());

            TextComponent coordinatesMessage = messageParser.parse("&6" + coordinates);

            player.sendActionBar(coordinatesMessage);
        }
    }
}
