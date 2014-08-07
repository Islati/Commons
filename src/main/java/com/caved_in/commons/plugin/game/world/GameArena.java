package com.caved_in.commons.plugin.game.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;

public interface GameArena {

	public int id();

	public String getArenaName();

	public String getWorldName();

	public World getWorld();

	public List<Location> getSpawnLocations();

	public boolean isEnabled();

	public boolean isLobby();

	public boolean isBreakable(Block block);

	public boolean isPlaceable(Block block);

}
