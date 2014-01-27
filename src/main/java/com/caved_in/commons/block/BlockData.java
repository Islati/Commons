package com.caved_in.commons.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

/**
 * @author Brandon Curtis
 * @version 1.0
 * @since 1.0
 */
public class BlockData {

	private byte blockData;
	private MaterialData blockMaterialData;
	private Location location;

	/**
	 * Creates a new Blockdata instance based on the block passed to it
	 *
	 * @param block block to instance a new blockdata for
	 */
	public BlockData(Block block) {
		this(block, block.getType());
	}

	/**
	 * Creates a new BlockData instance based on the block passed, and the material
	 * which to change the block to
	 *
	 * @param block          block to instance a new blockdata for
	 * @param changeMaterial material to change the block to
	 */
	public BlockData(Block block, Material changeMaterial) {
		this.location = block.getLocation();
		this.blockMaterialData = new MaterialData(changeMaterial);
		this.blockData = block.getData();
	}

	/**
	 * @return MaterialData instance for this BlockData instance
	 */
	public MaterialData getBlockMaterialData() {
		return blockMaterialData;
	}

	/**
	 * @return the material type of the block
	 */
	public Material getBlockType() {
		return blockMaterialData.getItemType();
	}

	/**
	 * @return byte item-data for this BlockData instance
	 */
	public byte getBlockData() {
		return blockData;
	}

	/**
	 * @return the location of the block attached to this block data
	 */
	public Location getLocation() {
		return location;
	}
}
