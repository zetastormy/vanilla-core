package org.zafire.studios.vanillacore;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.command.LobbyCommand;
import org.zafire.studios.vanillacore.listener.PlayerJoinListener;
import org.zafire.studios.vanillacore.task.AnnounceTask;
import org.zafire.studios.vanillacore.util.LocationSelector;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.PlayerCache;

public final class VanillaCorePlugin extends JavaPlugin {

    private Logger logger;
    private Server server;
    private PluginManager pluginManager;
    private LocationSelector locationSelector;
    private MessageParser messageParser;
    private PlayerCache playerCache;
    private BukkitScheduler bukkitScheduler;
    private Messenger messenger;

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
        bukkitScheduler.cancelTasks(this);
    }

    private void setInstances() {
        logger = getLogger();
        server = getServer();
        pluginManager = server.getPluginManager();
        locationSelector = new LocationSelector(server);
        messageParser = new MessageParser();
        playerCache = new PlayerCache();
        bukkitScheduler = server.getScheduler();
        messenger = server.getMessenger();
        logger.info("The object instances have been set!");
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        logger.info("The listeners have been registered!");
    }

    private void registerCommands() {
        getCommand("lobby").setExecutor(new LobbyCommand(this));
        logger.info("The command executors have been set!");
    }

    private void scheduleTasks() {
        new AnnounceTask(this);
        logger.info("The tasks have been scheduled!");
    }

    private void registerChannels() {
        messenger.registerOutgoingPluginChannel(this, "BungeeCord");
        logger.info("The channels have been registered!");
    }

    public LocationSelector getLocationSelector() {
        return locationSelector;
    }

    public MessageParser getMessageParser() {
        return messageParser;
    }

    public PlayerCache getPlayerCache() {
        return playerCache;
    }
}
