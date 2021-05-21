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
    private final BukkitScheduler scheduler;

    public CoordinatesTask(final VanillaCorePlugin plugin, final BukkitScheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;
        schedule();
    }

    public void schedule() {
        scheduler.runTaskTimerAsynchronously(plugin, () -> {
            final Server server = plugin.getServer();
            sendCoordinates(server);
        }, 300L, 40L);
    }

    private void sendCoordinates(final Server server) {
        for (Player player : server.getOnlinePlayers()) {
            String coordinates = createCoordinates(player).toString();
            TextComponent coordinatesMessage = MessageParser.parse("&8» &a" + coordinates + " &8«");

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
