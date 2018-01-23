package com.caved_in.commons.game.world;

import com.caved_in.commons.exceptions.WorldLoadException;
import com.caved_in.commons.game.MiniGame;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;

/**
 * Utilized via the {@link MiniGame} and {@link ArenaHandler} to provide cyclable worlds after 'rounds',
 * easy multi-world support, and restoration of maps after a round has been played / completed, etc.
 *
 * Each GameArena wraps a single World, and actions performed on an instance will only affect the world associated.
 */
public interface GameArena {

    /**
     * The ID registered to manage this GameArena.
     * @return id associated with the GameArena
     */
    int id();

    /**
     * The name of the arena.
     * @return name of the arena.
     */
    String getName();

    /**
     * Name of the world which this arena manages.
     * @return name of the world which this arena manages.
     */
    String getWorldName();

    /**
     * Retrieve the world that's managed by this GameArena.
     * @return the world that's managed by this GameArena
     */
    World getWorld();

    /**
     * Retrieve whether or not the world associated with this GameArena is loaded, or not.
     * @return true if the world is loaded, false otherwise.
     */
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

    default boolean isRegion() {
        return false;
    }

}
