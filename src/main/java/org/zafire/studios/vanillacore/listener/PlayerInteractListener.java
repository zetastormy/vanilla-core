package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zafire.studios.vanillacore.util.PlayerCache;

public final class PlayerInteractListener implements Listener {

    private final PlayerCache playerCache;

    public PlayerInteractListener(final PlayerCache playerCache) {
        this.playerCache = playerCache;
    }
    
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (player == null) return;

        final UUID playerUuid = player.getUniqueId();
        
        if (playerCache.isCached(playerUuid)) {
            playerCache.remove(playerUuid);
        }
    }
}
