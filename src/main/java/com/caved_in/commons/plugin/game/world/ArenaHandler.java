package com.caved_in.commons.plugin.game.world;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public interface ArenaHandler {

	public void addArena(Arena arena);

	public void cycleArena();

	public void setActiveArena(Arena arena);

	public Arena getActiveArena();

	public void loadArena(Arena arena);

	public void unloadArena(Arena arena);

	public void removeArena(Arena arena);

	default public void teleportAllToArena() {
		Players.stream().forEach(p -> teleportToRandomSpawn(p, getActiveArena()));
	}

	public static void teleportToRandomSpawn(Player player, Arena arena) {
		Players.teleport(player, arena.getRandomSpawn());
	}
}
