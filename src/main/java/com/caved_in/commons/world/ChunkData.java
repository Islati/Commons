package com.caved_in.commons.world;

import org.bukkit.Chunk;
import org.simpleframework.xml.Element;

public class ChunkData {

	@Element(name = "world")
	private String worldName;

	@Element(name = "x-pos")
	private final int x;

	@Element(name = "y-pos")
	private final int z;

	private final int[] cords;

	@Element(name = "state", type = ChunkState.class)
	private ChunkState state = ChunkState.UNPROCESSED;

	public ChunkData(Chunk chunk) {
		x = chunk.getX();
		z = chunk.getZ();
		cords = new int[]{x, z};
	}

	public ChunkData(@Element(name = "world") String worldName, @Element(name = "x-pos") int x,
					 @Element(name = "z-pos") int z, @Element(name = "state", type = ChunkState.class) ChunkState state) {
		this.worldName = worldName;
		this.x = x;
		this.z = z;
		this.cords = new int[]{x, z};
	}

	public boolean similarTo(Object o) {
		if (o instanceof ChunkData) {
			ChunkData chunkData = (ChunkData) o;
			return (equals(o)) || (x == chunkData.x && z == chunkData.z);
		} else if (o instanceof Chunk) {
			Chunk chunk = (Chunk) o;
			return x == chunk.getX() && z == chunk.getZ();
		} else {
			return false;
		}
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public int[] getCords() {
		return cords;
	}

	public Chunk getChunk() {
		return Chunks.getChunkAt(Worlds.getWorld(worldName), x, z);
	}

	public ChunkState getState() {
		return state;
	}

}
