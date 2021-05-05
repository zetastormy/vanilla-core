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
import org.zafire.studios.vanillacore.util.DeathCompassCreator;
import org.zafire.studios.vanillacore.util.PredefinedLocationSelector;
import org.zafire.studios.vanillacore.util.cache.DeathCache;

public final class PlayerRespawnListener implements Listener {

    private final VanillaCorePlugin plugin;
    private final PredefinedLocationSelector locationSelector;
    private final DeathCompassCreator deathCompassCreator;
    private final DeathCache deathCache;
    private final BukkitScheduler bukkitScheduler;

    public PlayerRespawnListener(final VanillaCorePlugin plugin, final PredefinedLocationSelector locationSelector, final DeathCache deathCache,
            final BukkitScheduler bukkitScheduler, final DeathCompassCreator deathCompassCreator) {
        this.plugin = plugin;
        this.locationSelector = locationSelector;
        this.deathCache = deathCache;
        this.bukkitScheduler = bukkitScheduler;
        this.deathCompassCreator = deathCompassCreator;
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        if (player == null)
            return;

        if (player.getBedSpawnLocation() == null) {
            player.teleportAsync(locationSelector.getRandomPredefinedLocation());
        }

        final ItemStack deathCompass = deathCompassCreator.create();

        final Inventory playerInventory = player.getInventory();
        playerInventory.addItem(deathCompass);

        final UUID playerUuid = player.getUniqueId();
        final Location deathLocation = deathCache.get(playerUuid);

        bukkitScheduler.runTaskLaterAsynchronously(plugin, () -> {
            player.setCompassTarget(deathLocation);
            deathCache.remove(playerUuid);
        }, 60L);
    }
}
