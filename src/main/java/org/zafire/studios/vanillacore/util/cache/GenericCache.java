package org.zafire.studios.vanillacore.util.cache;

import java.util.HashSet;
import java.util.Set;

public final class GenericCache<T> {

    private final Set<T> cacheContainer = new HashSet<>();

    public void add(final T t) {
        cacheContainer.add(t);
    }

    public void remove(final T t) {
        cacheContainer.remove(t);
    }

    public boolean isCached(final T t) {
        return cacheContainer.contains(t);
    }

    public int size() {
        return cacheContainer.size();
    }

    public boolean isEmpty() {
        return cacheContainer.isEmpty();
    }
}
