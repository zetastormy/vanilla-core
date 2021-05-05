package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zafire.studios.vanillacore.util.cache.GeneralCache;

public final class PlayerInteractListener implements Listener {

    private final GeneralCache<UUID> uuidCache;

    public PlayerInteractListener(final GeneralCache<UUID> uuidCache) {
        this.uuidCache = uuidCache;
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (player == null)
            return;

        final UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            uuidCache.remove(playerUuid);
        }
    }
}
