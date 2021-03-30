package org.zafire.studios.vanillacore.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class MessageSender {
    private final MessageParser messageParser;

    public MessageSender() {
        messageParser = new MessageParser();
    }

    public void sendMessage(final CommandSender sender, final boolean prefix, final String message) {
        final String prefixString = "&5&lZafire &8|| &7";

        if (prefix) {
            sender.sendMessage(messageParser.parse(prefixString + message));
        } else {
            sender.sendMessage(messageParser.parse(message));
        }
    }

    public void sendMessage(final Player player, final boolean prefix, final String message) {
        final String prefixString = "&5&lZafire &8|| &7";

        if (prefix) {
            player.sendMessage(messageParser.parse(prefixString + message));
        } else {
            player.sendMessage(messageParser.parse(message));
        }
    }
}
