package com.caved_in.commons.block;

import com.caved_in.commons.effects.EffectPlayer;
import com.caved_in.commons.items.ItemHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BlockHandler {

	public static Block getBlockAt(Location blockLocation) {
		return blockLocation.getWorld().getBlockAt(blockLocation);
	}

	/**
	 *
	 * @param blockLocation
	 * @param natural
	 * @return
	 */
	public static boolean breakBlockAt(Location blockLocation, boolean natural) {
		return breakBlockAt(blockLocation, natural,false);
	}

	/**
	 * Break a block at a specific location
	 * @param blockLocation
	 * @param natural
	 * @param playeffect
	 * @return
	 */
	public static boolean breakBlockAt(Location blockLocation, boolean natural, boolean playeffect) {
		Block block = getBlockAt(blockLocation);
		if (block != null) {
			return breakBlock(block,natural,playeffect);
		}
		return false;
	}

	/**
	 * Break a block either naturally, or un-naturally
	 * @param block block to "break" / remove
	 * @param natural whether or not to break naturally
	 * @return true if the block has been broken, false otherwise (Normally followed by a bukkit exception)
	 */
	public static boolean breakBlock(Block block, boolean natural) {
		//If it's supposed to be natural, return the bukkit call for breakNaturally, otherwise return our methods return
		return natural ? block.breakNaturally() : breakBlock(block,false,false);
	}

	public static boolean breakBlock(Block block, boolean natural, boolean playEffect) {
		if (natural) {
			//Return bukkits breakNaturally method on a block
			return block.breakNaturally();
		} else {
			//Change the material of the block to air
			setBlock(block,Material.AIR);
			//if the effect is to be played, play it!
			if (playEffect) {
				EffectPlayer.playBlockBreakEffect(block.getLocation(),4,block.getType());
			}
			return true;
		}
	}

	public static void setBlock(Block block, BlockData blockData) {
		//Update the blocks material data
		block.getState().setData(blockData.getBlockMaterialData());
		//Update the type
		block.setType(blockData.getBlockType());
		//Update the byte-data (Positioning, etc)
		block.setData(blockData.getBlockData());
		//Update the block state
		block.getState().update(true);
	}

	public static void setBlock(Block block, Material changeMaterial) {
		block.setType(changeMaterial);
		block.getState().setType(changeMaterial);
		block.getState().update(true);
	}

	public static void setBlock(BlockData blockData) {
		setBlock(blockData.getLocation(),blockData);
	}

	public static void setBlock(Location location, BlockData blockData) {
		setBlock(location.getWorld().getBlockAt(location), blockData);
	}

	public static boolean isOre(Block block) {
		return isOre(block.getType());
	}

	public static boolean isOre(Material material) {
		switch (material) {
			case COAL_ORE:
			case IRON_ORE:
			case DIAMOND_ORE:
			case EMERALD_ORE:
			case REDSTONE_ORE:
				return true;
			default:
				return false;
		}
	}

}
