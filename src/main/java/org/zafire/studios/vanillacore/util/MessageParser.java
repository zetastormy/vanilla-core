package org.zafire.studios.vanillacore.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public final class MessageParser {

    public TextComponent parse(final String rawMessage, final Player player) {
        final String message = sanitize(rawMessage, player);
        return Component.text(message);
    }

    public TextComponent parse(final String rawMessage) {
        ChatColor.translateAlternateColorCodes('&', rawMessage);
        return Component.text(rawMessage);
    }

    private String sanitize(final String rawMessage, final Player player) {
        return PlaceholderAPI.setPlaceholders(player, rawMessage);
    }

}
