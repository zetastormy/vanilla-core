package org.zafire.studios.vanillacore.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public final class MessageParser {
    
    public String parse(final String string, final Player player) {
        String stringReplacement;

        if (PlaceholderAPI.containsPlaceholders(string)) {
            Bukkit.getLogger().warning("Placeholders detected!");

            stringReplacement = PlaceholderAPI.setPlaceholders(player, string);
            
            Bukkit.getLogger().warning("String with placeholders: " + stringReplacement);
        }
        
        stringReplacement = replaceColors(string);

        return stringReplacement;
    }

    private String replaceColors(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
