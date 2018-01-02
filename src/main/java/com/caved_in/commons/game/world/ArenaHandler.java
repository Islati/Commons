package com.caved_in.commons.game.world;

import com.caved_in.commons.player.Players;
import com.caved_in.commons.yml.InvalidConfigurationException;
import org.bukkit.entity.Player;

/**
 * The managing interfaced used to handle all the {@link Arena} inside of a(n) {@link com.caved_in.commons.game.CraftGame}.
 *
 * Currently, the default implementation for a MiniGames ArenaHandler is {@link ArenaManager}
 */
public interface ArenaHandler<T extends GameArena> {

    /**
     * Retrieve an arena from the handler via its name.
     * @param name name of the arena to retrieve
     * @return the arena with the name given, or null if there's none by that name.
     */
    T getArena(String name);

    /**
     * Add an arena to the manager to be handled.
     * @param arena arena to add.
     * @return true if the arena was added, false otherwise.
     */
    boolean addArena(T arena);

    /**
     * Cycle the arena / "change maps".
     */
    void cycleArena();

    /**
     * Assign the active arena.
     * @param arena arena to set active.
     */
    void setActiveArena(T arena);

    /**
     * Retrieve the active arena.
     * @return Arena that's active, null if there's no active arena.
     */
    T getActiveArena();

    /**
     * Load the arena data.
     */
    void loadArenas() throws InvalidConfigurationException;

    /**
     * Save the arena data.
     */
    void saveArenas() throws InvalidConfigurationException;


    /**
     * Remove the arena from the arena handler.
     * @param arena arena to remove.
     */
    void removeArena(T arena);

    /**
     * @return whether or not this arena manager has arenas loaded.
     */
    boolean hasArenas();

    static void teleportToRandomSpawn(Player player, Arena arena) {
        if (player == null || arena == null) {
            return;
        }
        Players.teleport(player, arena.getRandomSpawn());
    }
}
