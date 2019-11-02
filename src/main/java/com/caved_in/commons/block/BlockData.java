package com.caved_in.commons.block;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.Collection;

/**
 * Used to cache the properties of a Block without storing the Block itself.
 */
public class BlockData {

    /* Byte data related to the blocks state */
    private byte blockByte;
    /* Material of this block */
    private Material material;
    /* Material data for this block */
    private MaterialData materialData = null;
    /* Location of the Block */
    private Location location;
    /* Light level of the block */
    private byte lightLevel;
    /* Light level the block gets from the sky */
    private byte lightFromSky;
    /* Light from Blocks */
    private byte lightFromBlocks;
    /* Whether or not this block is a liquid */
    private boolean liquid = false;

    public BlockData(Block block) {
        location = block.getLocation();
        material = block.getType();
        materialData = new MaterialData(material);
        blockByte = block.getData();
        lightLevel = block.getLightLevel();
        lightFromSky = block.getLightFromSky();
        lightFromBlocks = block.getLightFromBlocks();
        liquid = block.isLiquid();
    }

    /**
     * @return the block associated with this block data.
     */
    public Block getBlock() {
        return location.getBlock();
    }

    private void updateMaterialData() {
        materialData = new MaterialData(material);
    }

    /**
     * @return the blocks material data (material, and state)
     */
    public MaterialData getMaterialData() {
        return materialData;
    }

    /**
     * @return the type of material the block is.
     */
    public Material getType() {
        return materialData.getItemType();
    }
    /**
     * @return The light level the block provides.
     */
    public byte getLightLevel() {
        return lightLevel;
    }

    /**
     * @return the amount of light around this block
     */
    public byte getLightFromSky() {
        return lightFromSky;
    }

    /**
     * @return the world in which the block resides.
     */
    public World getWorld() {
        return location.getWorld();
    }

    /**
     * @return X-Coordinate of the blocks location.
     */
    public int getX() {
        return location.getBlockX();
    }

    /**
     * @return Y-Coordinate of the blocks location.
     */
    public int getY() {
        return location.getBlockY();
    }

    /**
     * @return Z-Coordinate of the blocks location.
     */
    public int getZ() {
        return location.getBlockZ();
    }

    /**
     * @return a byte representing the blocks state.
     */
    public byte getData() {
        return blockByte;
    }

    /**
     * @return the location of the block.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return the chunk which contains this block.
     */
    public Chunk getChunk() {
        return location.getChunk();
    }

    /**
     * Set the state of the block
     *
     * @param b data to assign to the blocks state.
     */
    public void setData(byte b) {
        this.blockByte = b;
    }

    /**
     * Change the blocks type.
     *
     * @param material material to change the block to.
     */
    public void setType(Material material) {
        this.material = material;
        updateMaterialData();
    }

    /**
     * Get the face of which the requested block resides at, relative to the block wrapped by BlockData.
     *
     * @param block block to get the face of relative to the parent.
     * @return face of which the requested block resides at, relative to the block wrapped by BlockData
     */
    public BlockFace getFace(Block block) {
        return getBlock().getFace(block);
    }

    /**
     * @return BlockState of the wrapped Block
     */
    public BlockState getState() {
        return getBlock().getState();
    }

    /**
     * @return biome that the block wrapped by BlockData resides in.
     */
    public Biome getBiome() {
        return getBlock().getBiome();
    }

    /**
     * @return The amount of power the block wrapped by blockdata is recieving via redstone.
     */
    public int getBlockPower() {
        return getBlock().getBlockPower();
    }

    /**
     * @return whether or not the block wrapped by BlockData is a liquid.
     */
    public boolean isLiquid() {
        return liquid;
    }

    /**
     * Break the block wrapped by BlockData, naturally.
     *
     * @return true if the block was broken, false otherwise.
     */
    public boolean breakNaturally() {
        return getBlock().breakNaturally();
    }

    /**
     * Break the block wrapped by BlockData, naturally, as if being broken using the given itemstack.
     *
     * @param itemStack itemstack to 'use' to break the block.
     * @return true if the block was broken, false otherwise.
     */
    public boolean breakNaturally(ItemStack itemStack) {
        return getBlock().breakNaturally(itemStack);
    }

    /**
     * @return a collection of items that the block wrapped will drop when broken.
     */
    public Collection<ItemStack> getDrops() {
        return getBlock().getDrops();
    }

    /**
     * Retrieve a collection of items that the wrapped block will drop when broke with the given itemstack.
     *
     * @param itemStack breaking firstPageEnabled to retrieve the drops from the block with.
     * @return collection of items that the wrapped block will drop when broke with the given itemstack
     */
    public Collection<ItemStack> getDrops(ItemStack itemStack) {
        return getBlock().getDrops(itemStack);
    }

    public byte getLightFromBlocks() {
        return lightFromBlocks;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Block) {
            Block block = (Block) o;
            return getWorld().equals(block.getWorld()) && getX() == block.getX() && getY() == block.getY() && getZ() == block.getZ() && getType() == block.getType();
        }

        if (o instanceof BlockData) {
            BlockData blockData = (BlockData) o;
            return blockData.getWorld().equals(getWorld()) && blockData.getType() == getType() && blockData.getX() == getX() && blockData.getZ() == getZ() && blockData.getY() == getY();
        }
        return false;
    }
}