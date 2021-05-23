package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.DeathCompassManager;
import org.zafire.studios.vanillacore.util.LocationSelector;
import org.zafire.studios.vanillacore.util.cache.DeathCache;

public final class PlayerRespawnListener implements Listener {

    private final VanillaCorePlugin plugin;
    private final DeathCache deathCache;
    private final BukkitScheduler scheduler;

    public PlayerRespawnListener(final VanillaCorePlugin plugin, final DeathCache deathCache,
            final BukkitScheduler scheduler) {
        this.plugin = plugin;
        this.deathCache = deathCache;
        this.scheduler = scheduler;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        if (player.getBedSpawnLocation() == null) {
            player.teleportAsync(LocationSelector.getRandomPredefinedLocation());
        }

        final ItemStack deathCompass = DeathCompassManager.getDeathCompass();

        final Inventory playerInventory = player.getInventory();
        playerInventory.addItem(deathCompass);

        final UUID playerUuid = player.getUniqueId();
        final Location deathLocation = deathCache.get(playerUuid);

        scheduler.runTaskLaterAsynchronously(plugin, () -> {
            player.setCompassTarget(deathLocation);
            deathCache.remove(playerUuid);
        }, 60L);
    }
}
