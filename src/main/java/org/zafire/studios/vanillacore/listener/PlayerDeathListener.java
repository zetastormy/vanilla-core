package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.zafire.studios.vanillacore.util.DeathCompassHelper;
import org.zafire.studios.vanillacore.util.cache.DeathCache;

public final class PlayerDeathListener implements Listener {

    private final DeathCache deathCache;

    public PlayerDeathListener(final DeathCache deathCache) {
        this.deathCache = deathCache;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID playerUuid = player.getUniqueId();
        Location deathLocation = player.getLocation();

        deathCache.add(playerUuid, deathLocation);

        event.getDrops().removeIf(item -> DeathCompassHelper.isDeathCompass(item));
    }
}
