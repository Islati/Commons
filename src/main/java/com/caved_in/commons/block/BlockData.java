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
 * @author Brandon Curtis
 * @version 1.0
 * @since 1.0
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

	public Block getBlock() {
		return location.getBlock();
	}

	private void updateMaterialData() {
		materialData = new MaterialData(material);
	}

	public MaterialData getMaterialData() {
		return materialData;
	}

	public Material getType() {
		return materialData.getItemType();
	}

	public int getTypeId() {
		return materialData.getItemTypeId();
	}

	public byte getLightLevel() {
		return lightLevel;
	}

	public byte getLightFromSky() {
		return lightFromSky;
	}

	public World getWorld() {
		return location.getWorld();
	}

	public int getX() {
		return location.getBlockX();
	}

	public int getY() {
		return location.getBlockY();
	}

	public int getZ() {
		return location.getBlockZ();
	}

	public byte getData() {
		return blockByte;
	}

	public Location getLocation() {
		return location;
	}

	public Chunk getChunk() {
		return location.getChunk();
	}

	public void setData(byte b) {
		this.blockByte = b;
	}

	public void setType(Material material) {
		this.material = material;
		updateMaterialData();
	}

	public BlockFace getFace(Block block) {
		return getBlock().getFace(block);
	}

	public BlockState getState() {
		return getBlock().getState();
	}

	public Biome getBiome() {
		return getBlock().getBiome();
	}

	public int getBlockPower() {
		return getBlock().getBlockPower();
	}

	public boolean isLiquid() {
		return liquid;
	}

	public boolean breakNaturally() {
		return getBlock().breakNaturally();
	}

	public boolean breakNaturally(ItemStack itemStack) {
		return getBlock().breakNaturally(itemStack);
	}

	public Collection<ItemStack> getDrops() {
		return getBlock().getDrops();
	}

	public Collection<ItemStack> getDrops(ItemStack itemStack) {
		return getBlock().getDrops(itemStack);
	}

	public int getId() {
		return getBlock().getTypeId();
	}

	public byte getLightFromBlocks() {
		return lightFromBlocks;
	}
}
