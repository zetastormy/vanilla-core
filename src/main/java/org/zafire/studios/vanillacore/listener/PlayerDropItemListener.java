package org.zafire.studios.vanillacore.listener;

import java.util.UUID;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextComponent;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

public final class PlayerDropItemListener implements Listener {

    private final GenericCache<UUID> uuidCache;
    private final MessageParser messageParser;

    public PlayerDropItemListener(final GenericCache<UUID> uuidCache, final MessageParser messageParser) {
        this.uuidCache = uuidCache;
        this.messageParser = messageParser;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerDropItem(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();

        if (player == null)
            return;

        final UUID playerUuid = player.getUniqueId();

        if (uuidCache.isCached(playerUuid)) {
            event.setCancelled(true);
        }

        final ItemStack itemDropped = event.getItemDrop().getItemStack();
        final net.minecraft.server.v1_16_R3.ItemStack itemDroppedNms = CraftItemStack.asNMSCopy(itemDropped);
        final NBTTagCompound itemDroppedCompound = (itemDroppedNms.hasTag() ? itemDroppedNms.getTag() : new NBTTagCompound());

        if (itemDroppedCompound.getByte("deathCompass") == 1) {
            event.getItemDrop().remove();

            final Sound sound = Sound.sound(Key.key("entity.item.break"), Sound.Source.NEUTRAL, 2, 0);
            player.playSound(sound);

            final TextComponent destroyMessage = messageParser
                    .parse("&2&lSurvival &8|| &7Tu buscadora de catacumbas ha sido destruida.", player);
            player.sendMessage(destroyMessage);
        }
    }
}
