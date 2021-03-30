package org.zafire.studios.vanillacore.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public final class MessageParser {

    public String parse(final String string, final Player player) {
        String stringReplacement;

        stringReplacement = PlaceholderAPI.setPlaceholders(player, string);

        Bukkit.getLogger().warning("String with placeholders: " + stringReplacement);

        return stringReplacement;
    }
}
