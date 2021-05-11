package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.DeathCompassCreator;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

public final class InventoryClickListener implements Listener {

    private final GenericCache<UUID> uuidCache;
    private final DeathCompassCreator deathCompassCreator;

    public InventoryClickListener(final GenericCache<UUID> uuidCache, final DeathCompassCreator deathCompassCreator) {
        this.uuidCache = uuidCache;
        this.deathCompassCreator = deathCompassCreator;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        if (player == null)
            return;

        final UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }

        final InventoryType inventoryType = event.getClickedInventory().getType();

        if (inventoryType != InventoryType.PLAYER) {
            final ItemStack deathCompass = deathCompassCreator.create();
            final ItemStack currentItem = event.getCurrentItem();

            if (currentItem.isSimilar(deathCompass)) {
                event.setCancelled(true);
            }
        }
    }
}
