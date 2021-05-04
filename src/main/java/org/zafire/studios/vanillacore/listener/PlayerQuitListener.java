package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zafire.studios.vanillacore.util.cache.GeneralCache;

public final class PlayerQuitListener implements Listener {

    private final GeneralCache<UUID> playerCache;

    public PlayerQuitListener(final GeneralCache<UUID> playerCache) {
        this.playerCache = playerCache;
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (player == null) return;

        final UUID playerUuid = player.getUniqueId();

        if (playerCache.isCached(playerUuid)) {
            playerCache.remove(playerUuid);
        }

        event.quitMessage(null);
    }
}
