package org.zafire.studios.vanillacore.task;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
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
            String coordinates = createCoordinates(player).toString();
            TextComponent coordinatesMessage = messageParser.parse("&8» &a" + coordinates + " &8«");

            player.sendActionBar(coordinatesMessage);
        }
    }

    private StringBuilder createCoordinates(final Player player) {
        Location playerLocation = player.getLocation();
        BlockFace playerFacing = player.getFacing();

        StringBuilder coordinates = new StringBuilder();
        coordinates.append((int) playerLocation.getX());
        coordinates.append(' ');
        coordinates.append((int) playerLocation.getY());
        coordinates.append(' ');
        coordinates.append((int) playerLocation.getZ());
        coordinates.append(' ');
        coordinates.append(playerFacing.toString());

        return coordinates;
    }
}
