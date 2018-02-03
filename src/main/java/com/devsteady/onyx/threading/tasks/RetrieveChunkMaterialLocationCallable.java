package com.devsteady.onyx.threading.tasks;

import com.devsteady.onyx.world.Chunks;
import com.devsteady.onyx.world.Worlds;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Map;
import java.util.concurrent.Callable;

public class RetrieveChunkMaterialLocationCallable implements Callable<Map<Location, Material>> {

    private int[] chunkCords;
    private String worldName;
    private Material[] materials;

    public RetrieveChunkMaterialLocationCallable(World world, int[] chunkCords, Material[] materials) {
        this.chunkCords = chunkCords;
        worldName = world.getName();
        this.materials = materials;
    }

    public RetrieveChunkMaterialLocationCallable(Chunk chunk, Material[] materials) {
        chunkCords = Chunks.getChunkCords(chunk);
        worldName = chunk.getWorld().getName();
        this.materials = materials;
    }

    @Override
    public Map<Location, Material> call() {
        return Chunks.getMaterialsInChunk(Worlds.getWorld(worldName).getChunkAt(chunkCords[0], chunkCords[1]), materials);
    }
}
