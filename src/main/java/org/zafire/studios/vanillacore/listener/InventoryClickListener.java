package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

import net.minecraft.server.v1_16_R3.NBTTagCompound;

public final class InventoryClickListener implements Listener {

    private final GenericCache<UUID> uuidCache;

    public InventoryClickListener(final GenericCache<UUID> uuidCache) {
        this.uuidCache = uuidCache;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }

        final InventoryAction action = event.getAction();
        final ClickType clickType = event.getClick();

        final ItemStack currentItem = event.getCurrentItem();
        final ItemStack cursorItem = event.getCursor();

        final net.minecraft.server.v1_16_R3.ItemStack cursorItemNms = CraftItemStack.asNMSCopy(cursorItem);
        final net.minecraft.server.v1_16_R3.ItemStack currentItemNms = CraftItemStack.asNMSCopy(currentItem);

        final NBTTagCompound currentItemCompound = (currentItemNms.hasTag() ? currentItemNms.getTag()
                : new NBTTagCompound());
        final NBTTagCompound cursorItemCompound = (cursorItemNms.hasTag() ? cursorItemNms.getTag()
                : new NBTTagCompound());

        if ((action.equals(InventoryAction.PLACE_ALL) || action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY))
                && event.getRawSlot() < event.getInventory().getSize()) {
            if (cursorItemCompound.getByte("deathCompass") == 1) {
                event.setCancelled(true);
                return;
            }
        }

        if (action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && event.isShiftClick()) {
            if (currentItemCompound.getByte("deathCompass") == 1) {
                event.setCancelled(true);
                return;
            }
        }

        if (clickType == ClickType.NUMBER_KEY && event.getRawSlot() < event.getInventory().getSize()) {
            final ItemStack itemSwapped = player.getInventory().getItem(event.getHotbarButton());
            final net.minecraft.server.v1_16_R3.ItemStack itemSwappedNms = CraftItemStack.asNMSCopy(itemSwapped);
            final NBTTagCompound itemSwappedCompound = (itemSwappedNms.hasTag() ? itemSwappedNms.getTag()
                    : new NBTTagCompound());

            if (itemSwappedCompound.getByte("deathCompass") == 1) {
                event.setCancelled(true);
            }
        }
    }
}
