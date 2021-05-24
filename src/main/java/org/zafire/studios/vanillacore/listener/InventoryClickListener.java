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
        final Player player = (Player) event.getWhoClicked();
        final UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }

        final Inventory inventory = event.getInventory();
        final InventoryAction action = event.getAction();
        final ClickType clickType = event.getClick();

        final boolean isTopInventory = event.getRawSlot() < inventory.getSize();
        final boolean isPlaceAction = action.equals(InventoryAction.PLACE_SOME)
                || action.equals(InventoryAction.PLACE_ONE) || action.equals(InventoryAction.PLACE_ALL);

        if ((isPlaceAction || action.equals(InventoryAction.SWAP_WITH_CURSOR)) && isTopInventory) {
            final ItemStack cursorItem = event.getCursor();
            cancelIfDeathCompass(event, cursorItem);
        }

        if (action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && event.isShiftClick()) {
            final ItemStack currentItem = event.getCurrentItem();
            cancelIfDeathCompass(event, currentItem);
        }

        if (clickType == ClickType.NUMBER_KEY && isTopInventory) {
            final ItemStack itemSwapped = player.getInventory().getItem(event.getHotbarButton());
            cancelIfDeathCompass(event, itemSwapped);
        }
    }

    private void cancelIfDeathCompass(final InventoryClickEvent event, final ItemStack item) {
        if (DeathCompassHelper.isDeathCompass(item)) {
            event.setCancelled(true);
        }
    }
}
