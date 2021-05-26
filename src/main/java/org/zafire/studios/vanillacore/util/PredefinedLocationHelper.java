package org.zafire.studios.vanillacore.util;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class PredefinedLocationHelper {

    private static final List<Location> LOCATIONS;

    static {
        World mainWorld = Bukkit.getWorld("world");

        LOCATIONS = List.of(new Location(mainWorld, 309, 79, 397), new Location(mainWorld, 701, 65, 467),
                new Location(mainWorld, 818, 128, 374), new Location(mainWorld, 1360, 84, 0),
                new Location(mainWorld, 1288, 78, -248), new Location(mainWorld, 1316, 71, -586));
    }

    public static Location getRandomPredefinedLocation() {
        Random random = new Random();
        return getPredefinedLocation(random.nextInt(LOCATIONS.size() - 1));
    }

    public static Location getPredefinedLocation(final int index) {
        return LOCATIONS.get(index);
    }
}
