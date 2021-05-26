package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.util.DeathCompassHelper;
import org.zafire.studios.vanillacore.util.PredefinedLocationHelper;
import org.zafire.studios.vanillacore.util.cache.DeathCache;

public final class PlayerRespawnListener implements Listener {

    private final Plugin plugin;
    private final DeathCache deathCache;
    private final BukkitScheduler scheduler;

    public PlayerRespawnListener(final Plugin plugin, final DeathCache deathCache, final BukkitScheduler scheduler) {
        this.plugin = plugin;
        this.deathCache = deathCache;
        this.scheduler = scheduler;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (player.getBedSpawnLocation() == null) {
            player.teleportAsync(PredefinedLocationHelper.getRandomPredefinedLocation());
        }

        ItemStack deathCompass = DeathCompassHelper.getDeathCompass();

        Inventory playerInventory = player.getInventory();
        playerInventory.addItem(deathCompass);

        UUID playerUuid = player.getUniqueId();
        Location deathLocation = deathCache.get(playerUuid);

        scheduler.runTaskLaterAsynchronously(plugin, () -> {
            player.setCompassTarget(deathLocation);
            deathCache.remove(playerUuid);
        }, 60L);
    }
}
