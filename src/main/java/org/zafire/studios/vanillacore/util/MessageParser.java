package org.zafire.studios.vanillacore.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public final class MessageParser {

    public static TextComponent parse(final String rawMessage, final Player player) {
        String message = sanitize(rawMessage, player);
        return Component.text(message);
    }

    public static TextComponent parse(final String rawMessage) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', rawMessage));
    }

    private static String sanitize(final String rawMessage, final Player player) {
        return PlaceholderAPI.setPlaceholders(player, rawMessage);
    }

}
