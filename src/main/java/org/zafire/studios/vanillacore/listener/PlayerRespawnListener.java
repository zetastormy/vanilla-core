package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.DeathCache;
import org.zafire.studios.vanillacore.util.DeathCompassCreator;
import org.zafire.studios.vanillacore.util.PredefinedLocationSelector;

public final class PlayerRespawnListener implements Listener {

    private final VanillaCorePlugin plugin;
    private final DeathCache deathCache;
    private final BukkitScheduler bukkitScheduler;
    private final DeathCompassCreator deathCompassCreator;

    public PlayerRespawnListener(final VanillaCorePlugin plugin) {
        this.plugin = plugin;
        deathCache = plugin.getDeathCache();
        Server server = plugin.getServer();
        bukkitScheduler = server.getScheduler();
        deathCompassCreator = plugin.getDeathCompassCreator();
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        if (player.getBedSpawnLocation() == null) {
            final PredefinedLocationSelector locationSelector = plugin.getLocationSelector();
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
