package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.DeathCompassCreator;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

import net.kyori.adventure.text.TextComponent;

public final class PlayerDropItemListener implements Listener {

    private final GenericCache<UUID> uuidCache;
    private final DeathCompassCreator deathCompassCreator;
    private final MessageParser messageParser;

    public PlayerDropItemListener(final GenericCache<UUID> uuidCache, final DeathCompassCreator deathCompassCreator, final MessageParser messageParser) {
        this.uuidCache = uuidCache;
        this.deathCompassCreator = deathCompassCreator;
        this.messageParser = messageParser;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDropItem(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();

        if (player == null)
            return;

        final UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }

        final ItemStack deathCompass = deathCompassCreator.create();
        final ItemStack itemDropped = event.getItemDrop().getItemStack();

        if (itemDropped.isSimilar(deathCompass)) {
            event.setCancelled(true);
            final TextComponent destroyMessage = messageParser.parse("&2&lSurvival &8|| &7Has tirado tu buscadora de catacumbas, y esta al perder el enlace contigo fue destruida.", player);
            player.sendMessage(destroyMessage);
        }
    }
}
