package org.zafire.studios.vanillacore.util;

import org.bukkit.ChatColor;

public final class MessageParser {
    
    public String parse(final String string) {
        final String stringReplacement = replaceColors(string);
        return stringReplacement;
    }

    private String replaceColors(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
