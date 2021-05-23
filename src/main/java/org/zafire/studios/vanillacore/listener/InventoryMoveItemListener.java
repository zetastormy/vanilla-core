package org.zafire.studios.vanillacore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.DeathCompassManager;

public class InventoryMoveItemListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryMoveItem(final InventoryMoveItemEvent event) {
        final ItemStack itemMoved = event.getItem();

        if (DeathCompassManager.isDeathCompass(itemMoved)) {
            event.setCancelled(true);
        }
    }
}
