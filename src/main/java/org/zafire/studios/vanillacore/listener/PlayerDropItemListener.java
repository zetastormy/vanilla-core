package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.DeathCompassHelper;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextComponent;

public final class PlayerDropItemListener implements Listener {

    private final GenericCache<UUID> uuidCache;

    public PlayerDropItemListener(final GenericCache<UUID> uuidCache) {
        this.uuidCache = uuidCache;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerDropItem(final PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }

        ItemStack itemDropped = event.getItemDrop().getItemStack();

        if (DeathCompassHelper.isDeathCompass(itemDropped)) {
            event.getItemDrop().remove();

            Sound sound = Sound.sound(Key.key("entity.item.break"), Sound.Source.NEUTRAL, 2, 0);
            player.playSound(sound);

            TextComponent destroyMessage = MessageParser
                    .parse("&2&lSurvival &8|| &7Tu buscadora de catacumbas ha sido destruida.", player);
            player.sendMessage(destroyMessage);
        }
    }
}
