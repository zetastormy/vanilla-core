package org.zafire.studios.vanillacore.util;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public final class MessageParser {

    public TextComponent parse(final String string, final Player player) {
        final String message = sanitize(string, player);
        return Component.text(message);
    }

    private String sanitize(final String string, final Player player) {
        return PlaceholderAPI.setPlaceholders(player, string);
    }

}
