package org.zafire.studios.vanillacore.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.LocationSelector;
import org.zafire.studios.vanillacore.util.MessageSender;

public final class PlayerJoinListener implements Listener {
    private final VanillaCorePlugin plugin;
    private LocationSelector locationSelector;
    private MessageSender messageSender;

    public PlayerJoinListener(final VanillaCorePlugin plugin) {
        this.plugin = plugin;
        messageSender = plugin.getMessageSender();
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if (event.getPlayer() == null) return;

        final Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            locationSelector = plugin.getLocationSelector();
            Location location = locationSelector.getRandomLocation();
            player.teleport(location);
        }
    }
}
