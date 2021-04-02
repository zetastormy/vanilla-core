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

            String coordinates = createCoordinates(playerLocation).toString();
            TextComponent coordinatesMessage = messageParser.parse("&8» &a" + coordinates + " &8«");

            player.sendActionBar(coordinatesMessage);
        }
    }

    private StringBuilder createCoordinates(final Location playerLocation) {
        StringBuilder coordinates = new StringBuilder();
        coordinates.append((int) playerLocation.getX());
        coordinates.append(' ');
        coordinates.append((int) playerLocation.getY());
        coordinates.append(' ');
        coordinates.append((int) playerLocation.getZ());
        coordinates.append(' ');
        coordinates.append(getDirection(playerLocation));

        return coordinates;
    }


    private String getDirection(final Location playerLocation) {
        double rotation = playerLocation.getYaw();
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }

        if (Math.abs(rotation) <= 45 || Math.abs(rotation - 360) <= 45) {
            return "+Z";
        } else if (Math.abs(rotation - 90) <= 45) {
            return "-X";
        } else if (Math.abs(rotation - 180) <= 45) {
            return "-Z";
        } else if (Math.abs(rotation - 270) <= 45) {
            return "+X";
        }

        return "";
    }
}
