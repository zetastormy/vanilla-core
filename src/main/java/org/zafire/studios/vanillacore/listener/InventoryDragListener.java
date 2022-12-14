package org.zafire.studios.vanillacore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.DeathCompassHelper;

public final class InventoryDragListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(final InventoryDragEvent event) {
        ItemStack oldItem = event.getOldCursor();

        if (DeathCompassHelper.isDeathCompass(oldItem)) {
            event.getRawSlots().stream().filter(slot -> slot < event.getInventory().getSize())
                    .forEach(slot -> event.setCancelled(true));
        }
    }
}
