package org.zafire.studios.vanillacore.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Server;

public final class LocationSelector {
    private final List<Location> locations;

    public LocationSelector(final Server server) {
        locations = new ArrayList<>();
        locations.add(new Location(server.getWorld("world"), 309, 79, 397));
        locations.add(new Location(server.getWorld("world"), 701, 65, 467));
        locations.add(new Location(server.getWorld("world"), 818, 128, 374));
        locations.add(new Location(server.getWorld("world"), 1360, 84, 0));
        locations.add(new Location(server.getWorld("world"), 1288, 78, -248));
        locations.add(new Location(server.getWorld("world"), 1316, 71, -586));
    }

    public Location getRandomLocation() {
        final Random randomNumber = new Random(6);
        return locations.get(randomNumber.nextInt(5));
    }
}
