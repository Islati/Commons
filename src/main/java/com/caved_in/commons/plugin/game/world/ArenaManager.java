package com.caved_in.commons.plugin.game.world;

import java.util.HashMap;
import java.util.Map;

public abstract class ArenaManager implements ArenaHandler {
	private Map<String, Arena> arenas = new HashMap<>();

	@Override
	public void addArena(Arena arena) {

	}

	@Override
	public void cycleArena() {

	}

	@Override
	public void setActiveArena(Arena arena) {

	}

	@Override
	public Arena getActiveArena() {
		return null;
	}

	@Override
	public void loadArena(Arena arena) {

	}

	@Override
	public void unloadArena(Arena arena) {

	}

	@Override
	public void removeArena(Arena arena) {

	}
}
