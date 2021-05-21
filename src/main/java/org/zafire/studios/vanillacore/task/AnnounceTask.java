package org.zafire.studios.vanillacore.task;

import java.util.Random;

import org.bukkit.Server;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.MessageParser;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextComponent;

public final class AnnounceTask {

    private final VanillaCorePlugin plugin;
    private final BukkitScheduler scheduler;

    public AnnounceTask(final VanillaCorePlugin plugin, final BukkitScheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;
        schedule();
    }

    public void schedule() {
        scheduler.runTaskTimerAsynchronously(plugin, () -> {
            final Server server = plugin.getServer();
            final Random random = new Random();
            final int randomNumber = random.nextInt(4);

            switch (randomNumber) {
                case 0:
                    announce("&6&lAnuncio &8|| &7Recuerda apoyarnos en la tienda del servidor&8: &6store.zafire.org",
                            server);
                    break;
                case 1:
                    announce("&6&lAnuncio &8|| &7No te olvides de seguirnos en Twitter&8: &6twitter.com/ZafireNT/",
                            server);
                    break;
                case 2:
                    announce("&6&lAnuncio &8|| &7Únete a nuestra comunidad de Discord&8: &6discord.zafire.org", server);
                    break;
                case 3:
                    announce("&6&lConsejo &8|| &7Recuerda leerlas nuestras normas y respetarlas&8: &6rules.zafire.org",
                            server);
                    break;
                default:
                    announce(
                            "&6&lAnuncio &8|| &7¡Comparte el servidor con tus conocidos para que la comunidad siga creciendo!",
                            server);
                    break;
            }
        }, 3000L, 6000L);
    }

    private void announce(final String rawMessage, final Server server) {
        final Sound sound = Sound.sound(Key.key("block.note_block.pling"), Sound.Source.BLOCK, 2, 0);
        server.playSound(sound);

        final TextComponent announceMessage = MessageParser.parse(rawMessage);
        server.sendMessage(announceMessage);
    }
}
