package org.zafire.studios.vanillacore;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.command.LobbyCommand;
import org.zafire.studios.vanillacore.listener.InventoryClickListener;
import org.zafire.studios.vanillacore.listener.PlayerDeathListener;
import org.zafire.studios.vanillacore.listener.PlayerDropItemListener;
import org.zafire.studios.vanillacore.listener.PlayerInteractListener;
import org.zafire.studios.vanillacore.listener.PlayerJoinListener;
import org.zafire.studios.vanillacore.listener.PlayerQuitListener;
import org.zafire.studios.vanillacore.listener.PlayerRespawnListener;
import org.zafire.studios.vanillacore.task.AnnounceTask;
import org.zafire.studios.vanillacore.task.CoordinatesTask;
import org.zafire.studios.vanillacore.util.DeathCompassCreator;
import org.zafire.studios.vanillacore.util.PredefinedLocationSelector;
import org.zafire.studios.vanillacore.util.cache.DeathCache;
import org.zafire.studios.vanillacore.util.cache.GenericCache;
import org.zafire.studios.vanillacore.util.MessageParser;

public final class VanillaCorePlugin extends JavaPlugin {

    private Logger logger;
    private Server server;
    private PluginManager pluginManager;
    private PredefinedLocationSelector locationSelector;
    private MessageParser messageParser;
    private GenericCache<UUID> uuidCache;
    private DeathCache deathCache;
    private BukkitScheduler scheduler;
    private Messenger messenger;
    private DeathCompassCreator deathCompassCreator;

    public VanillaCorePlugin() {
        super();
    }

    protected VanillaCorePlugin(final JavaPluginLoader loader, final PluginDescriptionFile description,
            final File dataFolder, final File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        setInstances();
        registerListeners();
        registerChannels();
        registerCommands();
        scheduleTasks();
    }

    @Override
    public void onDisable() {
        scheduler.cancelTasks(this);
    }

    private void setInstances() {
        logger = getLogger();
        server = getServer();
        pluginManager = server.getPluginManager();
        locationSelector = new PredefinedLocationSelector(server);
        messageParser = new MessageParser();
        uuidCache = new GenericCache<>();
        deathCache = new DeathCache();
        scheduler = server.getScheduler();
        messenger = server.getMessenger();
        deathCompassCreator = new DeathCompassCreator(messageParser);
        logger.info("The object instances have been set!");
    }

    private void registerListeners() {
        pluginManager.registerEvents(new InventoryClickListener(uuidCache), this);
        pluginManager.registerEvents(new PlayerDeathListener(deathCache), this);
        pluginManager.registerEvents(new PlayerDropItemListener(uuidCache, messageParser), this);
        pluginManager.registerEvents(new PlayerInteractListener(uuidCache), this);
        pluginManager.registerEvents(new PlayerJoinListener(messageParser, locationSelector), this);
        pluginManager.registerEvents(new PlayerQuitListener(uuidCache), this);
        pluginManager.registerEvents(
                new PlayerRespawnListener(this, locationSelector, deathCache, scheduler, deathCompassCreator), this);
        logger.info("The listeners have been registered!");
    }

    private void registerCommands() {
        getCommand("lobby").setExecutor(new LobbyCommand(this, uuidCache, messageParser, scheduler));
        logger.info("The command executors have been set!");
    }

    private void scheduleTasks() {
        new AnnounceTask(this, scheduler, messageParser);
        new CoordinatesTask(this, scheduler, messageParser);
        logger.info("The tasks have been scheduled!");
    }

    private void registerChannels() {
        messenger.registerOutgoingPluginChannel(this, "BungeeCord");
        logger.info("The channels have been registered!");
    }
}
