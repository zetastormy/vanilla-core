package org.zafire.studios.vanillacore;

import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.studios.vanillacore.command.LobbyCommand;
import org.zafire.studios.vanillacore.listener.InventoryClickListener;
import org.zafire.studios.vanillacore.listener.InventoryDragListener;
import org.zafire.studios.vanillacore.listener.InventoryMoveItemListener;
import org.zafire.studios.vanillacore.listener.PlayerDeathListener;
import org.zafire.studios.vanillacore.listener.PlayerDropItemListener;
import org.zafire.studios.vanillacore.listener.PlayerInteractListener;
import org.zafire.studios.vanillacore.listener.PlayerJoinListener;
import org.zafire.studios.vanillacore.listener.PlayerQuitListener;
import org.zafire.studios.vanillacore.listener.PlayerRespawnListener;
import org.zafire.studios.vanillacore.task.AnnounceTask;
import org.zafire.studios.vanillacore.task.CoordinatesTask;
import org.zafire.studios.vanillacore.util.cache.DeathCache;
import org.zafire.studios.vanillacore.util.cache.GenericCache;

public final class VanillaCorePlugin extends JavaPlugin {

    private PluginManager pluginManager;
    private GenericCache<UUID> uuidCache;
    private DeathCache deathCache;
    private BukkitScheduler scheduler;
    private Messenger messenger;

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
        Server server = getServer();
        
        pluginManager = server.getPluginManager();
        uuidCache = new GenericCache<>();
        deathCache = new DeathCache();
        scheduler = server.getScheduler();
        messenger = server.getMessenger();
    }

    private void registerListeners() {
        pluginManager.registerEvents(new InventoryClickListener(uuidCache), this);
        pluginManager.registerEvents(new InventoryDragListener(), this);
        pluginManager.registerEvents(new InventoryMoveItemListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(deathCache), this);
        pluginManager.registerEvents(new PlayerDropItemListener(uuidCache), this);
        pluginManager.registerEvents(new PlayerInteractListener(uuidCache), this);
        pluginManager.registerEvents(new PlayerJoinListener(this, scheduler), this);
        pluginManager.registerEvents(new PlayerQuitListener(uuidCache), this);
        pluginManager.registerEvents(new PlayerRespawnListener(this, deathCache, scheduler), this);
    }

    private void registerCommands() {
        getCommand("lobby").setExecutor(new LobbyCommand(this, uuidCache, scheduler));
    }

    private void scheduleTasks() {
        new AnnounceTask(this, scheduler);
        new CoordinatesTask(this, scheduler);
    }

    private void registerChannels() {
        messenger.registerOutgoingPluginChannel(this, "BungeeCord");
    }
}
