package org.zafire.studios.vanillacore.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public final class DeathCompassCreator {

    private final MessageParser messageParser;

    public DeathCompassCreator(final MessageParser messageParser) {
        this.messageParser = messageParser;
    }

    public ItemStack create() {
        final ItemStack deathCompass = new ItemStack(Material.COMPASS);
        final ItemMeta deathCompassMeta = createMeta(deathCompass);

        deathCompass.setItemMeta(deathCompassMeta);
        return deathCompass;
    }

    private ItemMeta createMeta(final ItemStack deathCompass) {
        final CompassMeta deathCompassMeta = (CompassMeta) deathCompass.getItemMeta();

        final TextComponent deathCompassName = messageParser.parse("&cBuscadora de catacumbas");

        final List<Component> deathCompassLore = new ArrayList<>();
        final TextComponent loreFirst = messageParser.parse("&8» &7Esta brújula apunta a tu lugar de muerte.");
        deathCompassLore.add(loreFirst);

        deathCompassMeta.displayName(deathCompassName);
        deathCompassMeta.lore(deathCompassLore);

        return deathCompassMeta;
    }
}
