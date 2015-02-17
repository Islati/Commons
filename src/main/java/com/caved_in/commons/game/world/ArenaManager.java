package com.caved_in.commons.game.world;

import com.caved_in.commons.exceptions.WorldLoadException;
import com.caved_in.commons.game.MiniGame;
import com.caved_in.commons.game.event.ArenaCycleEvent;
import com.caved_in.commons.game.event.ArenaLoadEvent;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.utilities.Str;
import com.caved_in.commons.world.Worlds;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

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

	public Arena getArena(World world) {
		return getArena(world.getName());
	}

	public boolean addArena(String worldName) {
		Logger logger = game.getLogger();
		if (arenas.containsKey(worldName)) {
			logger.info("Cannot add " + worldName + " as an arena with that name already exists");
			return false;
		}

		File worldFolder = new File(worldName);
		if (!worldFolder.exists() || !worldFolder.isDirectory()) {
			logger.info("No world found with name " + worldName);
			return false;
		}

		//Create a new arena for the given world
		if (!Worlds.exists(worldName)) {
			try {
				Worlds.load(worldName);
			} catch (WorldLoadException e) {
				logger.severe(String.format("Error adding arena for World '%s' ArenaManager::addArena - StackTrace: %s", worldName, Str.getStackStr(e)));
			}
			logger.info("Loaded " + worldName + " for the arena");
		}

		Arena arena = new Arena(Worlds.getWorld(worldName));
		addArena(arena);
		return true;
	}

	public boolean addArena(World world) {
		if (arenas.containsKey(world.getName())) {
			return false;
		}

		return addArena(new Arena(world));
	}

	@Override
	public boolean addArena(Arena arena) {
		arenas.put(arena.getWorldName(), arena);

		if (arena.isLobby() && lobby == null) {
			lobby = arena;
			lobby.setEnabled(true);
			setActiveArena(arena);
		}

		if (arenas.size() == 1) {
			setActiveArena(arena);
		}

		try {
			Worlds.load(arena.getWorldName());
		} catch (WorldLoadException e) {
			game.getLogger().severe("Error loading the world " + arena.getWorldName() + " in ArenaManager::addArena - " + Str.getStackStr(e));
		}

		/*
		Call the arena load event!
		Used by games that require an action to be performed whenever an arena is loaded.
		 */
		ArenaLoadEvent event = new ArenaLoadEvent(game, arena);
		Plugins.callEvent(event);

		if (arena.isEnabled()) {
			initializeArena(arena);
		}

		arena.getGame().saveArena(arena);
		return true;
	}

	private void initializeArena(Arena arena) {
		World arenaWorld = arena.getWorld();
		Worlds.cleanAllEntities(arenaWorld);

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
		Logger logger = game.getLogger();
		if (arenas.isEmpty()) {
			logger.info("Can't cycle arena because none are loaded");
			return;
		}

		List<String> arenaNames = Lists.newArrayList(arenas.keySet());

		if (arenas.size() < 3) {
			activeArena = arenaNames.get(0);
			logger.info("Not enough arenas loaded to cycle data. Min 3 required.");
			return;
		}


		String randomArena = "";
		do {
			randomArena = arenaNames.get(random.nextInt(arenaNames.size()));
		} while (activeArena.equalsIgnoreCase(randomArena));


		Arena active = getActiveArena();

		/*
		If this isn't the first arena that's been into play,
		then we can call an arena-cycle event!

		Just incase any of the minigames require a different arena to come into play ;)
		  */
		if (activeArena != null) {
			ArenaCycleEvent cycleEvent = new ArenaCycleEvent(game, active, getArena(randomArena));
			Plugins.callEvent(cycleEvent);
			if (cycleEvent.isCancelled()) {
				return;
			}

			randomArena = cycleEvent.getTo().getWorldName();
		}

		activeArena = randomArena;
		Players.teleportAllToSpawn(getActiveArena());
	}

	@Override
	public void setActiveArena(Arena arena) {
		activeArena = arena.getWorldName();
	}

	public void setActiveArena(String world) {
		activeArena = world;
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
		try {
			Worlds.load(arena.getWorldName());
		} catch (WorldLoadException e) {
			game.getLogger().severe("Error loading arena '" + arena.getArenaName() + "' (World: " + arena.getWorldName() + ") in ArenaManager::loadArena - " + Str.getStackStr(e));
		}

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

	@Override
	public boolean hasArenas() {
		return !arenas.isEmpty();
	}

	public void addSpawnToActiveArena(Location loc) {
		getActiveArena().addSpawn(loc);
	}

	public void teleportAllToArena() {
		Arena activeArena = getActiveArena();
		if (activeArena == null) {
			throw new NullPointerException("Unable to teleport players to the arena, as there's no active arena");
		}

		if (!activeArena.isWorldLoaded()) {
			try {
				activeArena.loadWorld();
			} catch (WorldLoadException e) {
				e.printStackTrace();
				//TODO Potentially cycle the arena, notify players, etc.
				return;
			}
		}

		Players.stream().forEach(p -> Players.teleport(p, activeArena.getRandomSpawn()));
	}


	public boolean isArena(String name) {
		return arenas.containsKey(name);
	}
}
