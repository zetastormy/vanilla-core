package org.zafire.studios.vanillacore.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.DeathCache;
import org.zafire.studios.vanillacore.util.LocationSelector;
import org.zafire.studios.vanillacore.util.MessageParser;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public final class PlayerRespawnListener implements Listener {

    private final VanillaCorePlugin plugin;
    private final DeathCache deathCache;
    private final MessageParser messageParser;

    public PlayerRespawnListener(final VanillaCorePlugin plugin) {
        this.plugin = plugin;
        deathCache = plugin.getDeathCache();
        messageParser = plugin.getMessageParser();
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        if (player.getBedSpawnLocation() == null) {
            LocationSelector locationSelector = plugin.getLocationSelector();
            final Location location = locationSelector.getRandomLocation();
            player.teleportAsync(location);
        }

        final ItemStack deathCompass = new ItemStack(Material.COMPASS);
        final CompassMeta deathCompassMeta = (CompassMeta) deathCompass.getItemMeta();

        final TextComponent deathCompassName = messageParser.parse("&cBuscadora de catacumbas");

        final List<Component> deathCompassLore = new ArrayList<>();
        final TextComponent loreFirst = messageParser.parse("&8» &7Esta brújula apunta a tu lugar de muerte.");
        deathCompassLore.add(loreFirst);

        final Location deathLocation = deathCache.get(player.getUniqueId());

        deathCompassMeta.displayName(deathCompassName);
        deathCompassMeta.lore(deathCompassLore);
        deathCompassMeta.setLodestone(deathLocation);

        deathCompass.setItemMeta(deathCompassMeta);

        final Inventory playerInventory = player.getInventory();
        playerInventory.addItem(deathCompass);
    }
}
