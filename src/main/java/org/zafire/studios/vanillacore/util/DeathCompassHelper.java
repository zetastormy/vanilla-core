package org.zafire.studios.vanillacore.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.minecraft.server.v1_16_R3.NBTTagByte;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

public final class DeathCompassHelper {

    private final static ItemStack DEATH_COMPASS;

    static {
        ItemStack compass = new ItemStack(Material.COMPASS);
        compass.setItemMeta(applyDeathCompassMeta(compass));

        DEATH_COMPASS = CraftItemStack.asBukkitCopy(applyDeathCompassTag(compass));
    }

    private static ItemMeta applyDeathCompassMeta(final ItemStack deathCompass) {
        CompassMeta deathCompassMeta = (CompassMeta) deathCompass.getItemMeta();

        TextComponent deathCompassName = MessageParser.parse("&cBuscadora de catacumbas");

        List<Component> deathCompassLore = new ArrayList<>();
        TextComponent loreFirst = MessageParser.parse("&8» &7Esta brújula apunta a tu lugar de muerte.");
        deathCompassLore.add(loreFirst);

        deathCompassMeta.displayName(deathCompassName);
        deathCompassMeta.lore(deathCompassLore);

        return deathCompassMeta;
    }

    private static net.minecraft.server.v1_16_R3.ItemStack applyDeathCompassTag(final ItemStack deathCompass) {
        net.minecraft.server.v1_16_R3.ItemStack deathCompassNms = CraftItemStack.asNMSCopy(deathCompass);

        NBTTagCompound deathCompassCompound = (deathCompassNms.hasTag() ? deathCompassNms.getTag()
                : new NBTTagCompound());
        deathCompassCompound.set("deathCompass", NBTTagByte.a((byte) 1));

        deathCompassNms.setTag(deathCompassCompound);

        return deathCompassNms;
    }

    public static boolean isDeathCompass(final ItemStack item) {
        net.minecraft.server.v1_16_R3.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemCompound = (itemNms.hasTag() ? itemNms.getTag() : new NBTTagCompound());

        return itemCompound.getByte("deathCompass") == 1;
    }

    public static ItemStack getDeathCompass() {
        return DEATH_COMPASS;
    }
}
