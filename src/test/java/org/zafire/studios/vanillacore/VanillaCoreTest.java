package org.zafire.studios.vanillacore;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zafire.studios.vanillacore.util.LocationSelector;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class VanillaCoreTest {
    private ServerMock server;
    private VanillaCorePlugin plugin;
    private PlayerMock player;
    private WorldMock world;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = (VanillaCorePlugin) MockBukkit.load(VanillaCorePlugin.class);
        player = server.addPlayer("ZetaStormy");
        world = server.addSimpleWorld("world");
    }

    @Test
    public void testLocation() {
        LocationSelector locationSelector = plugin.getLocationSelector();
        Location location = locationSelector.getRandomLocation();
        location.setWorld(world);
        player.teleport(location);
        player.assertLocation(location, 10);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}
