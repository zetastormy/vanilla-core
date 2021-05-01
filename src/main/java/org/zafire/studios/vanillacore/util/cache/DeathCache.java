package org.zafire.studios.vanillacore.util.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

public final class DeathCache {
    
    private final Map<UUID, Location> deathLocationCache = new HashMap<>();

    public void add(final UUID playerUuid, Location deathLocation) {
        deathLocationCache.put(playerUuid, deathLocation);
    }

    public void remove(final UUID playerUuid) {
        deathLocationCache.remove(playerUuid);
    }

    public Location get(final UUID playerUuid) {
        return deathLocationCache.get(playerUuid);
    }
}
