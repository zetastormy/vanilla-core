package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zafire.studios.vanillacore.util.PlayerCache;

public final class PlayerDropItemListener implements Listener {
    private final PlayerCache playerCache;

    public PlayerDropItemListener(final PlayerCache playerCache) {
        this.playerCache = playerCache;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDropItem(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();

        if (player == null) return;

        final UUID playerUuid = player.getUniqueId();

        if (playerCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }
    }
}
