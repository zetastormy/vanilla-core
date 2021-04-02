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
        coordinates.append(getCardinalDirection(playerLocation));

        return coordinates;
    }


    private String getCardinalDirection(final Location playerLocation) {
        double rotation = playerLocation.getYaw() - 180.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        if ((0.0D <= rotation) && (rotation < 22.5D)) {
            return "N";
        }
        if ((22.5D <= rotation) && (rotation < 67.5D)) {
            return "NE";
        }
        if ((67.5D <= rotation) && (rotation < 112.5D)) {
            return "E";
        }
        if ((112.5D <= rotation) && (rotation < 157.5D)) {
            return "SE";
        }
        if ((157.5D <= rotation) && (rotation < 202.5D)) {
            return "S";
        }
        if ((202.5D <= rotation) && (rotation < 247.5D) || (rotation <= -119.33) && (rotation > -179)) {
            return "SW";
        }
        if ((247.5D <= rotation) && (rotation < 292.5D) || (rotation <= -59.66) && (rotation > -119.33)) {
            return "W";
        }
        if ((292.5D <= rotation) && (rotation < 337.5D) || (rotation <= -0.0) && (rotation > -59.66)) {
            return "NW";
        }
        if ((337.5D <= rotation) && (rotation < 360.0D)) {
            return "N";
        }

        return "";
    }
}
