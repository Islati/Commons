package com.caved_in.commons.game.world;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * The managing interfaced used to handle all the {@link Arena} inside of a(n) {@link com.caved_in.commons.game.MiniGame}.
 *
 * Currently, the default implementation for a MiniGames ArenaHandler is {@link ArenaManager}
 */
public interface ArenaHandler {

    Arena getArena(String worldName);

    boolean addArena(Arena arena);

    void cycleArena();

    void setActiveArena(Arena arena);

    Arena getActiveArena();

    void loadArena(Arena arena);

    void unloadArena(Arena arena);

    void removeArena(Arena arena);

    boolean hasArenas();

    static void teleportToRandomSpawn(Player player, Arena arena) {
        if (player == null || arena == null) {
            return;
        }
        Players.teleport(player, arena.getRandomSpawn());
    }
}
