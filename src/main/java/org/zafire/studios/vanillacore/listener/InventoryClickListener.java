package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.zafire.studios.vanillacore.util.GeneralCache;

public final class InventoryClickListener implements Listener {

    private final GeneralCache<UUID> playerCache;

    public InventoryClickListener(final GeneralCache<UUID> playerCache) {
        this.playerCache = playerCache;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        if (player == null) return;

        final UUID playerUuid = player.getUniqueId();

        if (playerCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }
    }
}
