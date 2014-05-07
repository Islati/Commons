package com.caved_in.commons.world;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ChunkState {
	UNPROCESSED(0),
	PROCESSED(1);

	private static final Map<Integer, ChunkState> chunkStates = new HashMap<>();

	static {
		//Load all the chunk states into a map, indexed by their ID
		for (ChunkState chunkState : EnumSet.allOf(ChunkState.class)) {
			chunkStates.put(chunkState.getId(), chunkState);
		}
	}

	private int id;

	ChunkState(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static ChunkState getById(int id) {
		return chunkStates.get(id);
	}
}
