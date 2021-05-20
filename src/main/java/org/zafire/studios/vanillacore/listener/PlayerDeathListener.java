package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.DeathCompassCreator;
import org.zafire.studios.vanillacore.util.cache.DeathCache;

public final class PlayerDeathListener implements Listener {

    private final DeathCache deathCache;
    private final DeathCompassCreator deathCompassCreator;

    public PlayerDeathListener(final DeathCache deathCache, final DeathCompassCreator deathCompassCreator) {
        this.deathCache = deathCache;
        this.deathCompassCreator = deathCompassCreator;
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();

        if (player == null)
            return;

        final UUID playerUuid = player.getUniqueId();
        final Location deathLocation = player.getLocation();

        deathCache.add(playerUuid, deathLocation);

        final ItemStack deathCompass = deathCompassCreator.create();
        event.getDrops().removeIf(item -> item.isSimilar(deathCompass));
    }
}
