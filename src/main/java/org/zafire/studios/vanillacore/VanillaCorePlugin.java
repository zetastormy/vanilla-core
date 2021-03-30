package org.zafire.studios.vanillacore;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.zafire.studios.vanillacore.listener.PlayerJoinListener;
import org.zafire.studios.vanillacore.util.LocationSelector;
import org.zafire.studios.vanillacore.util.MessageParser;
import org.zafire.studios.vanillacore.util.MessageSender;

public final class VanillaCorePlugin extends JavaPlugin {
    private Logger logger;
    private Server server;
    private PluginManager pluginManager;
    private LocationSelector locationSelector;
    private MessageSender messageSender;
    private MessageParser messageParser;

    public VanillaCorePlugin() {
        super();
    }

    protected VanillaCorePlugin(final JavaPluginLoader loader, final PluginDescriptionFile description, final File dataFolder, final File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        setInstances();
        registerListeners();
    }

    private void setInstances() {
        logger = getLogger();
        server = getServer();
        pluginManager = server.getPluginManager();
        locationSelector = new LocationSelector(server);
        messageSender = new MessageSender();
        messageParser = new MessageParser();
        logger.info("Object instances has been set!");
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        logger.info("The listeners has been registered!");
    }

    public LocationSelector getLocationSelector() {
        return locationSelector;
    }
    
    public MessageSender getMessageSender() {
        return messageSender;
    }

    public MessageParser getMessageParser() {
        return messageParser;
    }
}
