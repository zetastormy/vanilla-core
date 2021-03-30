package org.zafire.studios.vanillacore.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.LocationSelector;
import org.zafire.studios.vanillacore.util.MessageParser;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public final class PlayerJoinListener implements Listener {
    private final VanillaCorePlugin plugin;
    private LocationSelector locationSelector;
    private MessageParser messageParser;

    public PlayerJoinListener(final VanillaCorePlugin plugin) {
        this.plugin = plugin;
        messageParser = plugin.getMessageParser();
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (player == null || (player.hasPlayedBefore() && !(player.hasPermission("sulphur.donator")))
                || player.hasPermission("sulphur.staff")) {
            event.joinMessage(null);
            return;
        }

        if (!player.hasPlayedBefore()) {
            if (!player.hasPermission("sulphur.donator")) {
                final TextComponent newPlayerMessage = Component
                        .text(messageParser.parse("&5&lZafire &8|| &7El usuario &6" + player.getName()
                                + " &7ha entrado por primera vez al servidor &8(&6#%server_unique_joins%&8)&7."));
                event.joinMessage(newPlayerMessage);
            }

            locationSelector = plugin.getLocationSelector();
            Location location = locationSelector.getRandomLocation();
            player.teleportAsync(location);
        }

        if (player.hasPermission("sulphur.donator") && player.hasPlayedBefore()) {
            final TextComponent donatorMessage = Component.text(messageParser
                    .parse("&5&lZafire &8|| &7El usuario &6" + player.getName() + " &7ha entrado al servidor."));
            event.joinMessage(donatorMessage);
        }
    }
}
