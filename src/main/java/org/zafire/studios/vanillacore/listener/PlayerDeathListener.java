package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.zafire.studios.vanillacore.util.cache.DeathCache;

public final class PlayerDeathListener implements Listener {

    private final DeathCache deathCache;

    public PlayerDeathListener(final DeathCache deathCache) {
        this.deathCache = deathCache;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();

        if (player == null)
            return;

        final UUID playerUuid = player.getUniqueId();
        final Location deathLocation = player.getLocation();

        deathCache.add(playerUuid, deathLocation);

        event.getDrops().removeIf(item -> CraftItemStack.asNMSCopy(item).getTag().getByte("deathCompass") == 1);
    }
}
