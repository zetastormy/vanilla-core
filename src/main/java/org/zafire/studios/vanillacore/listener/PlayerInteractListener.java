package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

public final class PlayerInteractListener implements Listener {

    private final GenericCache<UUID> uuidCache;

    public PlayerInteractListener(final GenericCache<UUID> uuidCache) {
        this.uuidCache = uuidCache;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            uuidCache.remove(playerUuid);
        }
    }
}
