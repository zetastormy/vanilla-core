package org.zafire.studios.vanillacore.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.util.DeathCompassHelper;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.PredefinedLocationHelper;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextComponent;

public final class PlayerJoinListener implements Listener {

    private final Plugin plugin;
    private final BukkitScheduler scheduler;

    public PlayerJoinListener(final Plugin plugin, final BukkitScheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;
    }

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

            final Location location = PredefinedLocationHelper.getRandomPredefinedLocation();
            player.teleportAsync(location);
        }

        if (player.hasPermission("sulphur.donator") && player.hasPlayedBefore()) {
            final TextComponent donatorPlayer = MessageParser
                    .parse("&5&lZafire &8|| &7El usuario &6" + player.getName() + " &7ha entrado al servidor.", player);

            event.joinMessage(donatorPlayer);
        }

        scheduler.runTaskLaterAsynchronously(plugin, () -> {
            final Inventory inventory = player.getInventory();

            if (inventory.contains(Material.COMPASS)) {
                ItemStack deathCompass = null;

                for (ItemStack item : inventory) {
                    if (DeathCompassHelper.isDeathCompass(item)) {
                        deathCompass = item;
                        break;
                    }
                }

                if (deathCompass != null) {
                    inventory.remove(deathCompass);

                    final Sound sound = Sound.sound(Key.key("entity.item.break"), Sound.Source.NEUTRAL, 2, 0);
                    player.playSound(sound);

                    final TextComponent destroyMessage = MessageParser.parse(
                            "&2&lSurvival &8|| &7Has salido del servidor y tu buscadora de catacumbas ha sido destruida.",
                            player);
                    player.sendMessage(destroyMessage);
                }
            }
        }, 60L);
    }
}
