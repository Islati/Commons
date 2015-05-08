package com.caved_in.commons.game.world;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public interface ArenaHandler {

    public Arena getArena(String worldName);

    public boolean addArena(Arena arena);

    public void cycleArena();

    public void setActiveArena(Arena arena);

    public Arena getActiveArena();

    public void loadArena(Arena arena);

    public void unloadArena(Arena arena);

    public void removeArena(Arena arena);

    public boolean hasArenas();

    public static void teleportToRandomSpawn(Player player, Arena arena) {
        if (player == null || arena == null) {
            return;
        }
        Players.teleport(player, arena.getRandomSpawn());
    }
}
