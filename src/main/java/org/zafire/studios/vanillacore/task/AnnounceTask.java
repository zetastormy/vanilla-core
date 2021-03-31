package org.zafire.studios.vanillacore.task;

import java.util.Random;

import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.MessageParser;

public class AnnounceTask {
    private final VanillaCorePlugin plugin;
    private final Server server;
    private final BukkitScheduler bukkitScheduler;
    private final MessageParser messageParser;

    public AnnounceTask(final VanillaCorePlugin plugin) {
        this.plugin = plugin;
        server = plugin.getServer();
        bukkitScheduler = server.getScheduler();
        messageParser = plugin.getMessageParser();
        schedule();
    }

    public void schedule() {
        bukkitScheduler.runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                final Random random = new Random();
                int randomNumber = random.nextInt(4);

                switch (randomNumber) {
                case 0:
                    announce("&6&lAnuncio &8|| &7Recuerda apoyarnos en la tienda del servidor&8: &6store.zafire.org");
                    break;
                case 1:
                    announce("&6&lAnuncio &8|| &7No te olvides de seguirnos en Twitter&8: &6twitter.com/ZafireNT/");
                    break;
                case 2:
                    announce("&6&lAnuncio &8|| &7Únete a nuestra comunidad de Discord&8: &6discord.zafire.org");
                    break;
                case 3:
                    announce("&6&lConsejo &8|| &7Recuerda leerlas nuestras normas y respetarlas&8: &6rules.zafire.org");
                    break;
                default:
                    announce(
                            "&6&lAnuncio &8|| &7¡Comparte el servidor con tus conocidos para que la comunidad siga creciendo!");
                    break;
                }
            }
        }, 3000L, 6000L);
    }

    private void announce(final String message) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 0);
            messageParser.parse(message, player);
        }
    }
}
