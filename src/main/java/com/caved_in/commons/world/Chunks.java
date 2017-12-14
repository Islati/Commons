package com.caved_in.commons.world;

import com.google.common.collect.Sets;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Chunks {
    private static final int MAX_X_RANGE = 16;
    private static final int MAX_Y_RANGE = 128;
    private static final int MAX_Z_RANGE = 16;


    private static Map<int[], ChunkData> chunkDataMap = new HashMap<>();
//	private MultiMap<Integer,Integer,ChunkData> chunkDataMap = new MultiMap<>()

    private static boolean initChunk(Chunk chunk) {
        if (!chunk.isLoaded() && !chunk.load()) {
            return false;
        }

        //Init the chunk data
        ChunkData chunkData = new ChunkData(chunk);
        //Get the co-ords for the chunk
        int[] chunkCords = getChunkCords(chunk);
        chunkDataMap.put(chunkCords, chunkData);
        return true;
    }

    private static boolean hasData(Chunk chunk) {
        return chunkDataMap.containsKey(getChunkCords(chunk));
    }

    private static ChunkData getData(Chunk chunk) {
        return chunkDataMap.get(getChunkCords(chunk));
    }

    private static void removeData(Chunk chunk) {
        chunkDataMap.remove(getChunkCords(chunk));
    }

    public static int[] getChunkCords(Chunk chunk) {
        return new int[]{chunk.getX(), chunk.getZ()};
    }

    public static boolean load(Chunk chunk) {
        return chunk.isLoaded() || chunk.load();
    }

    public static Map<Location, Material> getMaterialsInChunk(Chunk chunk, Material[] materials) {
        Set<Material> materialSet = Sets.newHashSet(materials);
        Map<Location, Material> chunkMaterials = new HashMap<>();

        for (int x = 0; x < MAX_X_RANGE; x++) {
            for (int y = 0; y < MAX_Y_RANGE; y++) {
                for (int z = 0; z < MAX_Z_RANGE; z++) {
                    Block block = chunk.getBlock(x, y, z);
                    Material blockType = block.getType();
                    //If the blocks material isn't of the desired type
                    if (!materialSet.contains(blockType)) {
                        continue;
                    }
                    chunkMaterials.put(block.getLocation(), blockType);
                }
            }
        }
        return chunkMaterials;
    }

    public static Chunk getChunkAt(World world, int x, int z) {
        return world.getChunkAt(x, z);
    }

    public static Chunk getChunkAt(World world, Location location) {
        return world.getChunkAt(location);
    }

    public static Chunk getChunkAt(World world, Block block) {
        return world.getChunkAt(block);
    }

    public static Chunk getChunk(Player player) {
        return player.getLocation().getChunk();
    }
}
