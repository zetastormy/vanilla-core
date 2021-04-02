package org.zafire.studios.vanillacore.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.LocationSelector;

public final class PlayerRespawnListener implements Listener {

    private final VanillaCorePlugin plugin;
    
    public PlayerRespawnListener(final VanillaCorePlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        if (player.getBedSpawnLocation() == null) {
            LocationSelector locationSelector = plugin.getLocationSelector();
            final Location location = locationSelector.getRandomLocation();
            player.teleportAsync(location);
        }
    }
}
