package org.zafire.studios.vanillacore.listener;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.NBTTagCompound;

public class InventoryMoveItemListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryMoveItem(final InventoryMoveItemEvent event) {
        final ItemStack itemMoved = event.getItem();
        final net.minecraft.server.v1_16_R3.ItemStack itemMovedNms = CraftItemStack.asNMSCopy(itemMoved);
        final NBTTagCompound itemMovedCompound = (itemMovedNms.hasTag() ? itemMovedNms.getTag() : new NBTTagCompound());

        if (itemMovedCompound.getByte("deathCompass") == 1) {
            event.setCancelled(true);
        }
    }
}
