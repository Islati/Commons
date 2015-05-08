package com.caved_in.commons.game.world;

import com.caved_in.commons.exceptions.WorldLoadException;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;

public interface GameArena {

    public int id();

    public String getArenaName();

    public String getWorldName();

    public World getWorld();

    public default boolean isWorldLoaded() {
        return Worlds.getWorld(getWorldName()) != null;
    }

    public default void loadWorld() throws WorldLoadException {
        String worldName = getWorldName();
        if (Worlds.exists(getWorldName())) {
            return;
        }

        if (!Worlds.load(worldName)) {
            throw new WorldLoadException("Unable to load the world '" + worldName + "'");
        }
    }

    public List<Location> getSpawnLocations();

    public boolean isEnabled();

    public boolean isLobby();

    public boolean isBreakable(Block block);

    public boolean isPlaceable(Block block);

}
