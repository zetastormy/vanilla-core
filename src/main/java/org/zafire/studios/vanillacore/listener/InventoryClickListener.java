package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
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

        if (player == null)
            return;

        final UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }

        final Inventory inventory = event.getClickedInventory();

        if (inventory.getType() != InventoryType.PLAYER) {
            final ItemStack currentItem = event.getCurrentItem();
            final net.minecraft.server.v1_16_R3.ItemStack currentItemNms = CraftItemStack.asNMSCopy(currentItem);

            final NBTTagCompound currentItemCompound = currentItemNms.getTag();

            if (currentItemCompound.getByte("deathCompass") == 1) {
                event.setCancelled(true);
            }
        }
    }
}
