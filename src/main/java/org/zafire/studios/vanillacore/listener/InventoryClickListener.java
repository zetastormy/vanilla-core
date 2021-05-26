package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.DeathCompassHelper;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

public final class InventoryClickListener implements Listener {

    private final GenericCache<UUID> uuidCache;

    public InventoryClickListener(final GenericCache<UUID> uuidCache) {
        this.uuidCache = uuidCache;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }

        Inventory inventory = event.getInventory();
        InventoryAction action = event.getAction();
        ClickType clickType = event.getClick();

        boolean isTopInventory = event.getRawSlot() < inventory.getSize();
        boolean isPlaceAction = action.equals(InventoryAction.PLACE_SOME) || action.equals(InventoryAction.PLACE_ONE)
                || action.equals(InventoryAction.PLACE_ALL);

        if ((isPlaceAction || action.equals(InventoryAction.SWAP_WITH_CURSOR)) && isTopInventory) {
            ItemStack cursorItem = event.getCursor();
            cancelIfDeathCompass(event, cursorItem);
        }

        if (action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && event.isShiftClick()) {
            ItemStack currentItem = event.getCurrentItem();
            cancelIfDeathCompass(event, currentItem);
        }

        if (clickType == ClickType.NUMBER_KEY && isTopInventory) {
            ItemStack itemSwapped = player.getInventory().getItem(event.getHotbarButton());
            cancelIfDeathCompass(event, itemSwapped);
        }
    }

    private void cancelIfDeathCompass(final InventoryClickEvent event, final ItemStack item) {
        if (DeathCompassHelper.isDeathCompass(item)) {
            event.setCancelled(true);
        }
    }
}
