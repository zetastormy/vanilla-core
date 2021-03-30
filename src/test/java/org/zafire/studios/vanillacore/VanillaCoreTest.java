package org.zafire.studios.vanillacore;

import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zafire.studios.vanillacore.util.LocationSelector;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

class VanillaCoreTest {
    private ServerMock server;
    private VanillaCorePlugin plugin;
    private PlayerMock player;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = (VanillaCorePlugin) MockBukkit.load(VanillaCorePlugin.class);
        player = server.addPlayer("ZetaStormy");
    }

    @Test
    public void testLocation() {
        LocationSelector locationSelector = plugin.getLocationSelector();
        Location location = locationSelector.getRandomLocation();
        player.teleport(location);
        player.assertLocation(location, 10);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}
