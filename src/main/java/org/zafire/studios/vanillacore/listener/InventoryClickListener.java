package org.zafire.studios.vanillacore.listener;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

import net.minecraft.server.v1_16_R3.NBTTagCompound;

public final class InventoryClickListener implements Listener {

    private final GenericCache<UUID> uuidCache;
    private final static Set<InventoryType> SHIFT_CLICK_ALLOWED_TYPES = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(InventoryType.BEACON, InventoryType.BREWING,
                    InventoryType.CRAFTING, InventoryType.FURNACE, InventoryType.WORKBENCH)));

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

        final Inventory inventory = event.getInventory();
        final InventoryAction action = event.getAction();
        final InventoryType type = inventory.getType();

        final ItemStack currentItem = event.getCurrentItem();
        final ItemStack cursorItem = event.getCursor();

        final net.minecraft.server.v1_16_R3.ItemStack cursorItemNms = CraftItemStack.asNMSCopy(cursorItem);
        final net.minecraft.server.v1_16_R3.ItemStack currentItemNms = CraftItemStack.asNMSCopy(currentItem);

        final NBTTagCompound currentItemCompound = (currentItemNms.hasTag() ? currentItemNms.getTag()
                : new NBTTagCompound());
        final NBTTagCompound cursorItemCompound = (cursorItemNms.hasTag() ? cursorItemNms.getTag()
                : new NBTTagCompound());

        if (action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
            if (currentItemCompound.getByte("deathCompass") == 1) {
                if (SHIFT_CLICK_ALLOWED_TYPES.contains(type)) {
                    return;
                }

                event.setCancelled(true);
                return;
            }
        }

        if (action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
            if (cursorItemCompound.getByte("deathCompass") == 1 || currentItemCompound.getByte("deathCompass") == 1) {
                if (SHIFT_CLICK_ALLOWED_TYPES.contains(type)) {
                    return;
                }

                event.setCancelled(true);
                return;
            }
        }

        if (action.equals(InventoryAction.HOTBAR_SWAP)) {

        }

        if (action.equals(InventoryAction.PLACE_ONE) || action.equals(InventoryAction.PLACE_SOME)
                || action.equals(InventoryAction.PLACE_ALL)) {

            if (cursorItemCompound.getByte("deathCompass") == 1 && event.getRawSlot() < inventory.getSize()) {
                event.setCancelled(true);
            }
        }
    }
}
