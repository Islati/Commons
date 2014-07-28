package com.caved_in.commons.plugin.game.world;

import com.caved_in.commons.Commons;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.plugin.game.MiniGame;
import com.caved_in.commons.world.Worlds;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.util.*;

public class ArenaManager implements ArenaHandler {
	private static final Random random = new Random();
	private Map<String, Arena> arenas = new HashMap<>();

	private Arena lobby;

	private String activeArena = "";

	private MiniGame game;

	public ArenaManager(MiniGame game) {
		this.game = game;
	}

	@Override
	public Arena getArena(String worldName) {
		return arenas.get(worldName);
	}

	public void addArena(String worldName) {
		if (arenas.containsKey(worldName)) {
			Commons.debug("Cannot add " + worldName + " as an arena with that name already exists");
			return;
		}

		File worldFolder = new File(worldName);
		if (!worldFolder.exists() || !worldFolder.isDirectory()) {
			Commons.debug("No world found with name " + worldName);
			return;
		}

		//Create a new arena for the given world
		if (!Worlds.exists(worldName)) {
			Worlds.load(worldName);
			Commons.debug("Loaded " + worldName + " for the arena");
		}

		Arena arena = new Arena(Worlds.getWorld(worldName));
		addArena(arena);
	}

	public void addArena(World world) {
		if (arenas.containsKey(world.getName())) {
			return;
		}

		addArena(new Arena(world));
	}

	@Override
	public void addArena(Arena arena) {
		arenas.put(arena.getWorldName(), arena);

		if (arena.isLobby() && lobby == null) {
			lobby = arena;
			lobby.setEnabled(true);
			setActiveArena(arena);
		}

		if (arenas.size() == 1) {
			setActiveArena(arena);
		}

		Worlds.load(arena.getWorldName());

		if (arena.isEnabled()) {
			initializeArena(arena);
		}

		arena.getGame().saveArena(arena);
	}

	private void initializeArena(Arena arena) {
		World arenaWorld = arena.getWorld();
		Entities.cleanAllEntities(arenaWorld);

		arenaWorld.setStorm(arena.isStormy());

		arenaWorld.setThundering(false);
		arenaWorld.setThunderDuration(6000);
		arenaWorld.setWeatherDuration(6000);

		arena.setGame(game);
		Worlds.setTimeDay(arenaWorld);
	}

	public Collection<Arena> getArenas() {
		return arenas.values();
	}

	@Override
	public void cycleArena() {
		if (arenas.isEmpty()) {
			Commons.debug("Can't cycle arena because none are loaded");
			return;
		}

		List<String> arenaNames = Lists.newArrayList(arenas.keySet());

		if (arenas.size() < 3) {
			activeArena = arenaNames.get(0);
			Commons.debug("Not enough arenas loaded to cycle data. Min 3 required.");
			return;
		}


		String randomArena = "";
		do {
			randomArena = arenaNames.get(random.nextInt(arenaNames.size()));
		} while (activeArena.equalsIgnoreCase(randomArena));

		activeArena = randomArena;
	}

	@Override
	public void setActiveArena(Arena arena) {
		activeArena = arena.getWorldName();
	}

	@Override
	public Arena getActiveArena() {
		if (Strings.isNullOrEmpty(activeArena)) {
			for (Map.Entry<String, Arena> entry : arenas.entrySet()) {
				activeArena = entry.getKey();
				break;
			}
		}
		return arenas.get(activeArena);
	}

	@Override
	public void loadArena(Arena arena) {
		Worlds.load(arena.getWorldName());

		if (arena.isEnabled()) {
			initializeArena(arena);
		}
	}

	@Override
	public void unloadArena(Arena arena) {
		Worlds.unload(arena.getWorldName());
	}

	@Override
	public void removeArena(Arena arena) {
		arenas.remove(arena.getWorldName());
	}

	public void addSpawnToActiveArena(Location loc) {
		getActiveArena().addSpawn(loc);
	}
}
