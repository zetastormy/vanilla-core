package org.zafire.studios.vanillacore.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public final class MessageParser {
    
    public String parse(final String string, final Player player) {
        String stringReplacement;

        if (PlaceholderAPI.containsPlaceholders(string)) {
            stringReplacement = PlaceholderAPI.setPlaceholders(player, string);
        }
        
        stringReplacement = replaceColors(string);

        return stringReplacement;
    }

    private String replaceColors(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
