package com.caved_in.commons.plugin.game.world;

import com.caved_in.commons.config.XmlLocation;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Root(name = "arena")
public class Arena implements GameArena {
	private static final Random random = new Random();

	@Element(name = "arena-id")
	private int id = 0;

	@Element(name = "arena-name")
	private String arenaName;

	@Element(name = "world-name")
	private String worldName;

	@Element(name = "enabled")
	private boolean enabled = true;

	@ElementList(name = "spawn-locations", type = XmlLocation.class, inline = true, entry = "spawn-point")
	private List<XmlLocation> spawns = new ArrayList<>();

	@Override
	public int id() {
		return id;
	}

	@Override
	public String getArenaName() {
		return null;
	}

	@Override
	public String getWorldName() {
		return worldName;
	}

	@Override
	public World getWorld() {
		return Worlds.getWorld(worldName);
	}

	@Override
	public List<Location> spawnLocations() {
		List<Location> locs = new ArrayList<>();
		for (XmlLocation loc : spawns) {
			locs.add(loc.getLocation());
		}
		return locs;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isLobby() {
		return false;
	}

	@Override
	public boolean isBreakable(Block block) {
		return false;
	}

	@Override
	public boolean isPlaceable(Block block) {
		return false;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void addSpawn(Location loc) {
		spawns.add(XmlLocation.fromLocation(loc));
	}

	public boolean hasSpawns() {
		return !spawns.isEmpty();
	}

	public Location getRandomSpawn() {
		return hasSpawns() ? spawns.get(random.nextInt(spawns.size())) : Worlds.getSpawn(getWorld());
	}
}
