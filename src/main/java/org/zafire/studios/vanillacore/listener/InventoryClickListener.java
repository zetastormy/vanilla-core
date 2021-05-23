package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.DeathCompassManager;
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

        final InventoryAction action = event.getAction();
        final ClickType clickType = event.getClick();

        if ((action.equals(InventoryAction.PLACE_ALL) || action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY))
                && event.getRawSlot() < event.getInventory().getSize()) {
            final ItemStack cursorItem = event.getCursor();

            if (DeathCompassManager.isDeathCompass(cursorItem)) {
                event.setCancelled(true);
                return;
            }
        }

        if (action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && event.isShiftClick()) {
            final ItemStack currentItem = event.getCurrentItem();

            if (DeathCompassManager.isDeathCompass(currentItem)) {
                event.setCancelled(true);
                return;
            }
        }

        if (clickType == ClickType.NUMBER_KEY && event.getRawSlot() < event.getInventory().getSize()) {
            final ItemStack itemSwapped = player.getInventory().getItem(event.getHotbarButton());

            if (DeathCompassManager.isDeathCompass(itemSwapped)) {
                event.setCancelled(true);
            }
        }
    }
}
