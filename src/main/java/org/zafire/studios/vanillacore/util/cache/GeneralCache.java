package org.zafire.studios.vanillacore.util.cache;

import java.util.HashSet;
import java.util.Set;

public final class GeneralCache<T> {
    
    private final Set<T> cacheContainer = new HashSet<>();

    public void add(final T playerUuid) {
        cacheContainer.add(playerUuid);
    }

    public void remove(final T playerUuid) {
        cacheContainer.remove(playerUuid);
    }

    public boolean isCached(final T playerUuid) {
        return cacheContainer.contains(playerUuid);
    }

    public int size() {
        return cacheContainer.size();
    }

    public boolean isEmpty() {
        return cacheContainer.isEmpty();
    }
}
