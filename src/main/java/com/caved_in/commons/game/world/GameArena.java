package com.caved_in.commons.game.world;

import com.caved_in.commons.exceptions.WorldLoadException;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;

/**
 * 
 */
public interface GameArena {

    int id();

    String getArenaName();

    String getWorldName();

    World getWorld();

    default boolean isWorldLoaded() {
        return Worlds.getWorld(getWorldName()) != null;
    }

    default void loadWorld() throws WorldLoadException {
        String worldName = getWorldName();
        if (Worlds.exists(getWorldName())) {
            return;
        }

        if (!Worlds.load(worldName)) {
            throw new WorldLoadException("Unable to load the world '" + worldName + "'");
        }
    }

    List<Location> getSpawnLocations();

    boolean isEnabled();

    boolean isLobby();

    boolean isBreakable(Block block);

    boolean isPlaceable(Block block);

}
