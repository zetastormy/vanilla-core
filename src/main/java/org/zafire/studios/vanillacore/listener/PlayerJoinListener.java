package org.zafire.studios.vanillacore.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.PredefinedLocationSelector;

import net.kyori.adventure.text.TextComponent;

public final class PlayerJoinListener implements Listener {

    private final MessageParser messageParser;
    private final PredefinedLocationSelector locationSelector;

    public PlayerJoinListener(final MessageParser messageParser, final PredefinedLocationSelector locationSelector) {
        this.messageParser = messageParser;
        this.locationSelector = locationSelector;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        event.joinMessage(null);

        if (!player.hasPlayedBefore()) {
            if (!player.hasPermission("sulphur.donator")) {
                final TextComponent newPlayer = messageParser.parse(
                        "&5&lZafire &8|| &7El usuario &6" + player.getName()
                                + " &7ha entrado por primera vez al servidor &8(&6#%server_unique_joins%&8)&7.",
                        player);

                event.joinMessage(newPlayer);
            }

            final Location location = locationSelector.getRandomPredefinedLocation();
            player.teleportAsync(location);
        }

        if (player.hasPermission("sulphur.donator") && player.hasPlayedBefore()) {
            final TextComponent donatorPlayer = messageParser
                    .parse("&5&lZafire &8|| &7El usuario &6" + player.getName() + " &7ha entrado al servidor.", player);

            event.joinMessage(donatorPlayer);
        }
    }
}
