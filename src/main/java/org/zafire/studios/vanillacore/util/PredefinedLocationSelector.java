package org.zafire.studios.vanillacore.util;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

public final class PredefinedLocationSelector {

    private final List<Location> locations;
    private final Random random;

    public PredefinedLocationSelector(final Server server) {
        final World world = server.getWorld("world");

        locations = List.of(new Location(world, 309, 79, 397), new Location(world, 701, 65, 467),
                new Location(world, 818, 128, 374), new Location(world, 1360, 84, 0),
                new Location(world, 1288, 78, -248), new Location(world, 1316, 71, -586));

        random = new Random();
    }

    public Location getRandomPredefinedLocation() {
        return getPredefinedLocation(random.nextInt(locations.size() - 1));
    }

    public Location getPredefinedLocation(int index) {
        return locations.get(index);
    }
}
