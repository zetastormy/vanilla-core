package org.zafire.studios.vanillacore.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.LocationSupplier;

import net.kyori.adventure.text.TextComponent;

public final class PlayerJoinListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        event.joinMessage(null);

        if (!player.hasPlayedBefore()) {
            if (!player.hasPermission("sulphur.donator")) {
                final TextComponent newPlayer = MessageParser.parse(
                        "&5&lZafire &8|| &7El usuario &6" + player.getName()
                                + " &7ha entrado por primera vez al servidor &8(&6#%server_unique_joins%&8)&7.",
                        player);

                event.joinMessage(newPlayer);
            }

            final Location location = LocationSupplier.getRandomPredefinedLocation();
            player.teleportAsync(location);
        }

        if (player.hasPermission("sulphur.donator") && player.hasPlayedBefore()) {
            final TextComponent donatorPlayer = MessageParser
                    .parse("&5&lZafire &8|| &7El usuario &6" + player.getName() + " &7ha entrado al servidor.", player);

            event.joinMessage(donatorPlayer);
        }
    }
}
