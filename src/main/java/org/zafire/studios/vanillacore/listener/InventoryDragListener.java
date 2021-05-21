package org.zafire.studios.vanillacore.listener;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.NBTTagCompound;

public class InventoryDragListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(final InventoryDragEvent event) {
        final ItemStack oldItem = event.getOldCursor();
        final net.minecraft.server.v1_16_R3.ItemStack oldItemNms = CraftItemStack.asNMSCopy(oldItem);
        final NBTTagCompound oldItemCompound = (oldItemNms.hasTag() ? oldItemNms.getTag() : new NBTTagCompound());

        if (oldItemCompound.getByte("deathCompass") == 1) {
            event.getRawSlots().stream().filter(slot -> slot < event.getInventory().getSize())
                    .forEach(slot -> event.setCancelled(true));
        }
    }
}
