package com.caved_in.commons.block;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.effects.EffectPlayer;
import com.caved_in.commons.items.BlockID;
import com.caved_in.commons.location.LocationHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;

import java.util.HashSet;
import java.util.Set;

public class BlockHandler {
	public static final Set<Integer> HOLLOW_MATERIALS = new HashSet<Integer>();
	private static final HashSet<Byte> TRANSPARENT_MATERIALS = new HashSet<Byte>();

	static {
		HOLLOW_MATERIALS.add(Material.AIR.getId());
		HOLLOW_MATERIALS.add(Material.SAPLING.getId());
		HOLLOW_MATERIALS.add(Material.POWERED_RAIL.getId());
		HOLLOW_MATERIALS.add(Material.DETECTOR_RAIL.getId());
		HOLLOW_MATERIALS.add(Material.LONG_GRASS.getId());
		HOLLOW_MATERIALS.add(Material.DEAD_BUSH.getId());
		HOLLOW_MATERIALS.add(Material.YELLOW_FLOWER.getId());
		HOLLOW_MATERIALS.add(Material.RED_ROSE.getId());
		HOLLOW_MATERIALS.add(Material.BROWN_MUSHROOM.getId());
		HOLLOW_MATERIALS.add(Material.RED_MUSHROOM.getId());
		HOLLOW_MATERIALS.add(Material.TORCH.getId());
		HOLLOW_MATERIALS.add(Material.REDSTONE_WIRE.getId());
		HOLLOW_MATERIALS.add(Material.SEEDS.getId());
		HOLLOW_MATERIALS.add(Material.SIGN_POST.getId());
		HOLLOW_MATERIALS.add(Material.WOODEN_DOOR.getId());
		HOLLOW_MATERIALS.add(Material.LADDER.getId());
		HOLLOW_MATERIALS.add(Material.RAILS.getId());
		HOLLOW_MATERIALS.add(Material.WALL_SIGN.getId());
		HOLLOW_MATERIALS.add(Material.LEVER.getId());
		HOLLOW_MATERIALS.add(Material.STONE_PLATE.getId());
		HOLLOW_MATERIALS.add(Material.IRON_DOOR_BLOCK.getId());
		HOLLOW_MATERIALS.add(Material.WOOD_PLATE.getId());
		HOLLOW_MATERIALS.add(Material.REDSTONE_TORCH_OFF.getId());
		HOLLOW_MATERIALS.add(Material.REDSTONE_TORCH_ON.getId());
		HOLLOW_MATERIALS.add(Material.STONE_BUTTON.getId());
		HOLLOW_MATERIALS.add(Material.SNOW.getId());
		HOLLOW_MATERIALS.add(Material.SUGAR_CANE_BLOCK.getId());
		HOLLOW_MATERIALS.add(Material.DIODE_BLOCK_OFF.getId());
		HOLLOW_MATERIALS.add(Material.DIODE_BLOCK_ON.getId());
		HOLLOW_MATERIALS.add(Material.PUMPKIN_STEM.getId());
		HOLLOW_MATERIALS.add(Material.MELON_STEM.getId());
		HOLLOW_MATERIALS.add(Material.VINE.getId());
		HOLLOW_MATERIALS.add(Material.FENCE_GATE.getId());
		HOLLOW_MATERIALS.add(Material.WATER_LILY.getId());
		HOLLOW_MATERIALS.add(Material.NETHER_WARTS.getId());

		try {
			HOLLOW_MATERIALS.add(Material.CARPET.getId());
		} catch (NoSuchFieldError e) {
			Commons.messageConsole(Messages.OUTDATED_VERSION);
		}

		//All hollow materials are transparant materials
		for (Integer integer : HOLLOW_MATERIALS) {
			TRANSPARENT_MATERIALS.add(integer.byteValue());
		}
		//Water is transparent, though not hollow
		TRANSPARENT_MATERIALS.add((byte) Material.WATER.getId());
		TRANSPARENT_MATERIALS.add((byte) Material.STATIONARY_WATER.getId());
	}

	public static Material getBlockMaterial(Block block) {
		Material itemMaterial = block.getType();
		switch (itemMaterial) {
			case WOODEN_DOOR:
				itemMaterial = Material.WOOD_DOOR;
				break;
			case IRON_DOOR_BLOCK:
				itemMaterial = Material.IRON_DOOR;
				break;
			case SIGN_POST:
			case WALL_SIGN:
				itemMaterial = Material.SIGN;
				break;
			case CROPS:
				itemMaterial = Material.SEEDS;
				break;
			case CAKE_BLOCK:
				itemMaterial = Material.CAKE;
				break;
			case BED_BLOCK:
				itemMaterial = Material.BED;
				break;
			case REDSTONE_WIRE:
				itemMaterial = Material.REDSTONE;
				break;
			case REDSTONE_TORCH_OFF:
			case REDSTONE_TORCH_ON:
				itemMaterial = Material.REDSTONE_TORCH_ON;
				break;
			case DIODE_BLOCK_OFF:
			case DIODE_BLOCK_ON:
				itemMaterial = Material.DIODE;
				break;
			case DOUBLE_STEP:
				itemMaterial = Material.STEP;
				break;
			case FIRE:
				itemMaterial = Material.AIR;
				break;
			case PUMPKIN_STEM:
				itemMaterial = Material.PUMPKIN_SEEDS;
				break;
			case MELON_STEM:
				itemMaterial = Material.MELON_SEEDS;
				break;
		}
		return itemMaterial;
	}

	public static int getBlockId(Block block) {
		return getBlockId(block, true);
	}

	public static int getBlockId(Block block, boolean itemsId) {
		return itemsId ? getBlockMaterial(block).getId() : block.getType().getId();
	}

	/**
	 * Check whether or not a block is hollow
	 * @param block block to check
	 * @return true if the block is hollow (meaning it can't be stood on / walked over without falling through), false otherwise
	 */
	public static boolean isHollowBlock(Block block) {
		return HOLLOW_MATERIALS.contains(getBlockId(block,false));
	}

	/**
	 * Check whether or not a block is transparent
	 * @param block block to check
	 * @return true if the block is hollow (meaning it can't be stood on / walked over without falling through), false otherwise
	 */
	public static boolean isTransparentBlock(Block block) {
		return TRANSPARENT_MATERIALS.contains((byte)getBlockId(block,false));
	}

	/**
	 * Determine whether or not the block above the block passed is air
	 * @param block block to check
	 * @return true if the block above the block passed is air, false otherwise
	 */
	public static boolean isBlockAboveAir(Block block) {
		return isHollowBlock(getBlockAbove(block));
	}

	/**
	 * Determine whether or not the block passed is a damaging block, or would be damaging
	 * if a player were to be at that location
	 * @param block block to check
	 * @return true if the block could damage players / entities, false otherwise
	 */
	public static boolean isBlockDamaging(Block block) {
		Block blockBelow = getBlockBelow(block);
		switch (blockBelow.getType()) {
			case LAVA:
			case STATIONARY_LAVA:
			case FIRE:
			case BED_BLOCK:
				return true;
			default:
				return !HOLLOW_MATERIALS.contains(getBlockId(block, false)) || !HOLLOW_MATERIALS.contains(getBlockId(getBlockAbove(block), false));
		}
	}

	/**
	 * Return the block at a specific location
	 *
	 * @param blockLocation location of the block
	 * @return Block that was at the given location, or null if none was there
	 */
	public static Block getBlockAt(Location blockLocation) {
		return blockLocation.getWorld().getBlockAt(blockLocation);
	}

	/**
	 * Break a block at specific location
	 *
	 * @param blockLocation location to break the block
	 * @param natural       whether or not to break naturally
	 * @return true if the block was broken, false if it doesn't exist or wasn't broken
	 */
	public static boolean breakBlockAt(Location blockLocation, boolean natural) {
		return breakBlockAt(blockLocation, natural, false);
	}

	/**
	 * Break a block at a specific location
	 *
	 * @param blockLocation location to break the block
	 * @param natural       whether or not to break naturally
	 * @param playeffect    whether or not to play the block-break effect
	 * @return true if the block was broken, false if it wasn't broken, or no block is at the location
	 */
	public static boolean breakBlockAt(Location blockLocation, boolean natural, boolean playeffect) {
		Block block = getBlockAt(blockLocation);
		if (block != null) {
			return breakBlock(block, natural, playeffect);
		}
		return false;
	}

	/**
	 * Break a block either naturally, or un-naturally
	 *
	 * @param block   block to "break" / remove
	 * @param natural whether or not to break naturally
	 * @return true if the block has been broken, false otherwise (Normally followed by a bukkit exception)
	 */
	public static boolean breakBlock(Block block, boolean natural) {
		//If it's supposed to be natural, return the bukkit call for breakNaturally, otherwise return our methods return
		return natural ? block.breakNaturally() : breakBlock(block, false, false);
	}

	/**
	 * Breaks a block
	 *
	 * @param block      block to break
	 * @param natural    whether or not to break naturally
	 * @param playEffect whether or not to play the block-break effect
	 * @return true if the block was broken, false otherwise
	 */
	public static boolean breakBlock(Block block, boolean natural, boolean playEffect) {
		if (natural) {
			//Return bukkits breakNaturally method on a block
			return block.breakNaturally();
		} else {
			//Change the material of the block to air
			setBlock(block, Material.AIR);
			//if the effect is to be played, play it!
			if (playEffect) {
				EffectPlayer.playBlockBreakEffect(block.getLocation(), 4, block.getType());
			}
			return true;
		}
	}

	/**
	 * Change a block to the data inside the blockdata
	 *
	 * @param block     block to change
	 * @param blockData blockdata used to update the block
	 */
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

	/**
	 * Change the material of a block
	 *
	 * @param block          block to change
	 * @param changeMaterial material to set the block to
	 */
	public static void setBlock(Block block, Material changeMaterial) {
		block.setType(changeMaterial);
		block.getState().setType(changeMaterial);
		block.getState().update(true);
	}

	/**
	 * Set blockdata for a specific block
	 *
	 * @param blockData blockdata to update at
	 */
	public static void setBlock(BlockData blockData) {
		setBlock(blockData.getLocation(), blockData);
	}

	/**
	 * Set a block at a specific location to the data in a blockData object
	 *
	 * @param location  location of the block
	 * @param blockData blockdata used to update the block
	 */
	public static void setBlock(Location location, BlockData blockData) {
		setBlock(location.getWorld().getBlockAt(location), blockData);
	}

	/**
	 * Check whether or not a block is an ore.
	 *
	 * @param block block to check
	 * @return true if the material is the ore-block of coal, iron, diamond, emerald, redstone, gold, or lapis
	 */
	public static boolean isOre(Block block) {
		return isOre(block.getType());
	}

	/**
	 * Check whether or not a material is an ore.
	 *
	 * @param material material to check
	 * @return true if the material is the ore-block of coal, iron, diamond, emerald, redstone, gold, or lapis
	 */
	public static boolean isOre(Material material) {
		switch (material) {
			case COAL_ORE:
			case IRON_ORE:
			case DIAMOND_ORE:
			case EMERALD_ORE:
			case REDSTONE_ORE:
			case GOLD_ORE:
			case LAPIS_ORE:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Check a block at a specific XYZ Cordinate for a world, and destroy all the blocks with the
	 * id defined by <i>required</i> around the block
	 *
	 * @param world    World which we're getting the block
	 * @param x        x-Axis coordinate for the blocks location
	 * @param y        y-Axis coordinate for the blocks location
	 * @param z        z-Axis coordinate for the blocks location
	 * @param required id of the block to destroy
	 */
	private void checkAndDestroyAround(World world, int x, int y, int z, int required) {
		checkAndDestroy(world, x, y, z + 1, required);
		checkAndDestroy(world, x, y, z - 1, required);
		checkAndDestroy(world, x, y + 1, z, required);
		checkAndDestroy(world, x, y - 1, z, required);
		checkAndDestroy(world, x + 1, y, z, required);
		checkAndDestroy(world, x - 1, y, z, required);
	}

	/**
	 * Check if a block at a specific location is the same as the block defined by <i>required</i>
	 * and then destroy the block if so
	 *
	 * @param world    World which we're getting the block
	 * @param x        x-Axis coordinate for the blocks location
	 * @param y        y-Axis coordinate for the blocks location
	 * @param z        z-Axis coordinate for the blocks location
	 * @param required id of the block to destroy
	 */
	private void checkAndDestroy(World world, int x, int y, int z, int required) {
		if (world.getBlockTypeIdAt(x, y, z) == required) {
			world.getBlockAt(x, y, z).setTypeId(BlockID.AIR);
		}
	}

	/**
	 * Spawn primed tnt at a specific location
	 * @param location location to spawn tnt
	 */
	public static void spawnTNT(Location location) {
		location.getWorld().spawn(location, TNTPrimed.class);
	}

	/**
	 * Spawn a specific amount of primed tnt at a specific location
	 * @param location location to spawn tnt
	 * @param amount amount of tnt to spawn
	 */
	public static void spawnTNT(Location location, int amount) {
		for (int i = 0; i < amount; i++) {
			spawnTNT(location);
		}
	}

	/**
	 * Gets the block above the block passed
	 * @param block block to get the block above
	 * @return block that was above the previous block (may be null if block didn't exist)
	 */
	public static Block getBlockAbove(Block block) {
		int[] xyz = LocationHandler.getXYZ(block.getLocation());
		return block.getWorld().getBlockAt(xyz[0],xyz[1] - 1, xyz[2]);
	}

	/**
	 * Get a block below the block passed
	 * @param block block to get the block below
	 * @return block that was below the previous block (may be null if block did not exist)
	 */
	public static Block getBlockBelow(Block block) {
		int[] xyz = LocationHandler.getXYZ(block.getLocation());
		return block.getWorld().getBlockAt(xyz[0],xyz[1] + 1, xyz[2]);
	}

}
