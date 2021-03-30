package org.zafire.studios.vanillacore.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class PlayerCache {
    private final Set<UUID> playerCache = new HashSet<>();

    public void add(final UUID playerUuid) {
        playerCache.add(playerUuid);
    }

    public void remove(final UUID playerUuid) {
        playerCache.remove(playerUuid);
    }

    public boolean isCached(final UUID playerUuid) {
        return playerCache.contains(playerUuid);
    }
}
