package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.DeathCache;

public final class PlayerDeathListener implements Listener {

    private final DeathCache deathCache;

    public PlayerDeathListener(final VanillaCorePlugin plugin) {
        deathCache = plugin.getDeathCache();
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final UUID playerUuid = player.getUniqueId();
        final Location deathLocation = player.getLocation();

        deathCache.add(playerUuid, deathLocation);
    }
}
