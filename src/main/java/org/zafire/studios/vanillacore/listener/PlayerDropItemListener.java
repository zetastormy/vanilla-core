package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zafire.studios.vanillacore.util.GeneralCache;

public final class PlayerDropItemListener implements Listener {

    private final GeneralCache<UUID> playerCache;

    public PlayerDropItemListener(final GeneralCache<UUID> playerCache) {
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
