package org.zafire.studios.vanillacore.task;

import java.util.Random;

import org.bukkit.Server;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.VanillaCorePlugin;
import org.zafire.studios.vanillacore.util.MessageParser;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextComponent;

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
        bukkitScheduler.runTaskTimerAsynchronously(plugin, () -> {
            final Random random = new Random();
            final int randomNumber = random.nextInt(4);

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
        }, 3000L, 6000L);
    }

    private void announce(final String rawMessage) {
        final Sound sound = Sound.sound(Key.key("NOTE_BLOCK_PLING"), Sound.Source.BLOCK, 2, 0);
        server.playSound(sound);

        final TextComponent announceMessage = messageParser.parse(rawMessage);
        server.sendMessage(announceMessage);
    }
}
