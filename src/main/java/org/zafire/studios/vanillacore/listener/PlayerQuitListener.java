package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

public final class PlayerQuitListener implements Listener {

    private final GenericCache<UUID> uuidCache;

    public PlayerQuitListener(final GenericCache<UUID> uuidCache) {
        this.uuidCache = uuidCache;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        event.quitMessage(null);

        Player player = event.getPlayer();
        UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            uuidCache.remove(playerUuid);
        }
    }
}
