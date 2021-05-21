package org.zafire.studios.vanillacore;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Location;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.zafire.studios.vanillacore.util.LocationSelector;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

@Disabled("MockBukkit doesn't support Paper API")
public class PredefinedLocationTeleportationTest {
    private ServerMock server;
    private PlayerMock player;
    private WorldMock world;

    @BeforeAll
    public void setUp() {
        server = MockBukkit.mock();
        player = server.addPlayer("ZetaStormy");
        world = server.addSimpleWorld("world");
    }

    @Test
    public void testTeleportationToRandomPredefinedLocation() {
        Location location = LocationSelector.getRandomPredefinedLocation();
        location.setWorld(world);
        assertTrue(player.teleport(location), "Player teleportation failed!");
        player.assertLocation(location, 10);
    }

    @AfterAll
    public void tearDown() {
        MockBukkit.unmock();
    }
}
