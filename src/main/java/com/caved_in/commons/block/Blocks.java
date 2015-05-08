package com.caved_in.commons.block;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.Effects;
import com.caved_in.commons.item.BlockID;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.threading.tasks.BlockRegenThread;
import com.caved_in.commons.threading.tasks.BlocksRegenThread;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.ListUtils;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.world.Worlds;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.*;
import java.util.stream.Collectors;

public class Blocks {

    private static final Random random = new Random();

    public static final long BLOCK_REGEN_DELAY = TimeHandler.getTimeInTicks(8, TimeType.SECOND);

    /**
     * Set of the item-ids which materials are hollow
     */
    private static final Set<Integer> HOLLOW_MATERIALS = new HashSet<>();
    public static final HashSet<Byte> TRANSPARENT_MATERIALS = new HashSet<>();

    /* Initialize the materials which are hollow */
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
            Chat.debug(Messages.OUTDATED_VERSION);
        }

        //All hollow materials are transparant materials
        for (Integer integer : HOLLOW_MATERIALS) {
            TRANSPARENT_MATERIALS.add(integer.byteValue());
        }
        //Water is transparent, though not hollow
        TRANSPARENT_MATERIALS.add((byte) Material.WATER.getId());
        TRANSPARENT_MATERIALS.add((byte) Material.STATIONARY_WATER.getId());
    }

    /**
     * Convert a list of locations into a list of blocks!
     *
     * @param locs locations to get the blocks for.
     * @return list of all the locations that the blocks are at.
     */
    public static List<Block> getBlocks(List<Location> locs) {
        return locs.stream().map(Location::getBlock).collect(Collectors.toList());
    }

    /**
     * Get the distance between the nearest block of the given type, and the location.
     *
     * @param loc            location to begin the search from
     * @param searchMaterial material to search for
     * @param depth          depth (and radius) of how far to search
     * @return the distance between the location and the searched material, or -1 if nothing was found.
     */
    public static int getBlockTypeDistance(Location loc, Material searchMaterial, int depth) {
        World world = loc.getWorld();
        double baseX = loc.getX();
        double baseY = loc.getY();
        double baseZ = loc.getZ();

        //From 0 to the deepest of depths allowed, loop through all the blocks!
        for (int depthLevel = 0; depthLevel < depth; ++depthLevel) {

            int deepZ;
            int deepY;

            //Bottom up on Z axis & Y Axis- check if the blocks match the type we're searching for!
            for (deepZ = -depthLevel; deepZ <= depthLevel; ++deepZ) {
                for (deepY = -depthLevel; deepY <= depthLevel; ++deepY) {

                    Block blockAtPlus = getBlockAt(Locations.getLocation(world, baseX + deepZ, baseY + depthLevel, baseZ + deepY));
                    Block blockAtMinus = getBlockAt(Locations.getLocation(world, baseX + deepZ, baseY - depthLevel, baseZ + deepY));

                    if (blockAtPlus.getType() == searchMaterial || blockAtMinus.getType() == searchMaterial) {
                        return depthLevel;
                    }
                }
            }

            for (deepZ = -depthLevel; deepZ <= depthLevel; ++deepZ) {
                for (deepY = (-depthLevel + 1); deepY <= (depthLevel - 1); ++deepY) {
                    Block blockAtPlus = getBlockAt(Locations.getLocation(world, baseX + deepZ, baseY + deepY, baseZ + depthLevel));
                    Block blockAtMinus = getBlockAt(Locations.getLocation(world, baseX + deepZ, baseY + deepY, baseZ - depthLevel));

                    if (blockAtPlus.getType() == searchMaterial || blockAtMinus.getType() == searchMaterial) {
                        return depthLevel;
                    }
                }
            }

            for (deepZ = (-depthLevel + 1); deepZ < (depthLevel - 1); ++deepZ) {
                for (deepY = (-depthLevel + 1); deepY <= (depthLevel - 1); ++deepY) {
                    Block blockAtPlus = getBlockAt(Locations.getLocation(world, baseX + depthLevel, baseY + deepY, baseZ + deepZ));
                    Block blockAtMinus = getBlockAt(Locations.getLocation(world, baseX - depthLevel, baseY + deepY, baseZ + deepZ));

                    if (blockAtPlus.getType() == searchMaterial || blockAtMinus.getType() == searchMaterial) {
                        return depthLevel;
                    }
                }
            }
        }

        return -1;
    }

    /**
     * Gets the corresponding material for blocks and materials.
     * <p>
     * For example: {@link org.bukkit.Material#WOODEN_DOOR} is the block-correspondant
     * for {@link org.bukkit.Material#WOOD_DOOR}.
     * <p/>
     * Passing a block to this method will result in the corresponding item-stack material for the block.
     * </p>
     *
     * @param block block to get the material of
     * @return the corresponding item-stack material for the block.
     */
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

    /**
     * Gets the material id for the block passed
     *
     * @param block block to get the id for
     * @return id of the block
     * @see #getBlockId(org.bukkit.block.Block, boolean)
     */
    public static int getBlockId(Block block) {
        return getBlockId(block, true);
    }

    /**
     * Gets either the block id, or material id based on the parameters.s
     *
     * @param block   block to get the id for
     * @param itemsId whether or not to retrieve the item-stack id, or the actual block material id
     * @return integer for the item id requested (either block or material)
     */
    public static int getBlockId(Block block, boolean itemsId) {
        return itemsId ? getBlockMaterial(block).getId() : block.getType().getId();
    }

    /**
     * Check whether or not a block at a specific location is a solid or not.
     *
     * @param loc location to check if the block is solid.
     * @return true if the block is a solid block, false if it's hollow, air, or liquid (Not solid).
     */
    public static boolean isSolid(Location loc) {
        return getBlockAt(loc).getType().isSolid();
    }

    /**
     * Check whether or not a block is hollow
     *
     * @param block block to check
     * @return true if the block is hollow (meaning it can't be stood on / walked over without falling through), false otherwise
     */
    public static boolean isHollowBlock(Block block) {
        return HOLLOW_MATERIALS.contains(getBlockId(block, false));
    }

    /**
     * Check whether or not a block is transparent
     *
     * @param block block to check
     * @return true if the block is hollow (meaning it can't be stood on / walked over without falling through), false otherwise
     */
    public static boolean isTransparentBlock(Block block) {
        return TRANSPARENT_MATERIALS.contains((byte) getBlockId(block, false));
    }

    /**
     * Determine whether or not the block above the block passed is air
     *
     * @param block block to check
     * @return true if the block above the block passed is air, false otherwise
     * @see #isHollowBlock(org.bukkit.block.Block)
     * @see #isBlockAboveAir(org.bukkit.block.Block)
     */
    public static boolean isBlockAboveAir(Block block) {
        return isHollowBlock(getBlockAbove(block));
    }

    /**
     * Determine whether or not the block passed is a damaging block, or would be damaging
     * if a player were to be at that location
     *
     * @param block block to check
     * @return true if the block could damage players / entities, false otherwise
     * @see #getBlockBelow(org.bukkit.block.Block)
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
     * Returns the block at a specific location
     *
     * @param blockLocation location of the block
     * @return Block that was at the given location, or null if none was there
     */
    public static Block getBlockAt(Location blockLocation) {
        return blockLocation.getWorld().getBlockAt(blockLocation);
    }

    /**
     * Breaks the block at specific location without showing the block-break effect
     *
     * @param blockLocation location to break the block
     * @param natural       whether or not to break naturally
     * @return true if the block was broken, false if it doesn't exist or wasn't broken
     * @see #breakBlock(org.bukkit.block.Block, boolean, boolean)
     */
    public static boolean breakBlockAt(Location blockLocation, boolean natural) {
        return breakBlockAt(blockLocation, natural, false);
    }

    /**
     * Breaks the block at a specific location
     *
     * @param blockLocation location to break the block
     * @param natural       whether or not to break naturally
     * @param playeffect    whether or not to play the block-break effect
     * @return true if the block was broken, false if it wasn't broken, or no block is at the location
     * @see #breakBlock(org.bukkit.block.Block, boolean)
     */
    public static boolean breakBlockAt(Location blockLocation, boolean natural, boolean playeffect) {
        Block block = getBlockAt(blockLocation);
        if (block != null) {
            return breakBlock(block, natural, playeffect);
        }
        return false;
    }

    /**
     * Breaks the block either naturally, or un-naturally
     *
     * @param block   block to "break" / remove
     * @param natural whether or not to break naturally
     * @return true if the block has been broken, false otherwise (Normally followed by a bukkit exception)
     * @see #breakBlock(org.bukkit.block.Block, boolean, boolean)
     */
    public static boolean breakBlock(Block block, boolean natural) {
        //If it's supposed to be natural, return the bukkit call for breakNaturally, otherwise return our methods return
        return natural ? block.breakNaturally() : breakBlock(block, false, false);
    }

    /**
     * Breaks the block
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
                Effects.playBlockBreakEffect(block.getLocation(), 4, block.getType());
            }
            return true;
        }
    }

    /**
     * CURRENTLY BROKEN: WILL FIX / OPTIMISE.
     * Force a player to break a tree, optionally the leaves and whether or not to drop items when breaking the blocks.
     * <p>
     * Calls a block-break-event for each block that's attempting to be broken, that way not allowing any bypass of plugins & protections.
     *
     * @param player    player to break the tree.
     * @param block     base block of the tree.
     * @param leaves    whether or not to break the leaves.
     * @param dropItems whether or not to drop the items when blocks are broken.
     */
    @Deprecated
    public static void breakTreeSafely(Player player, Block block, boolean leaves, boolean dropItems) {
        Material type = block.getType();
        ItemStack drop = null;
        if (type == Material.LOG || type == Material.LOG_2 || (leaves && (type == Material.LEAVES || type == Material.LEAVES_2))) {
            drop = Items.convertBlockToItem(block);
            BlockBreakEvent breakEvent = new BlockBreakEvent(block, player);

            Plugins.callEvent(breakEvent);

            if (breakEvent.isCancelled()) {
                return;
            }

            breakBlock(block, true);
            if (dropItems) {
                Worlds.dropItem(block.getLocation(), drop);
            }

			/*
            For all the blocks surrounding the parent, we're going to continue breaking the
			blocks (logs, and potentially leaves) until they're all gone!
			 */

            for (Block b : getBlocksSurrounding(block)) {
                breakTreeSafely(player, b, leaves, dropItems);
            }
        }
    }

    /**
     * CURRENTLY BROKEN & BUGGY.
     * Force a tree to be broken.
     *
     * @param block     base-block of the tree to break.
     * @param leaves    whether or not to break the leaves aswell.
     * @param dropItems whether or not to drop the items when breaking the tree.
     */
    public static void breakTree(Block block, boolean leaves, boolean dropItems) {
        Material type = block.getType();
        ItemStack drop = null;
        if (type == Material.LOG || type == Material.LOG_2 || (leaves && (type == Material.LEAVES || type == Material.LEAVES_2))) {
            drop = Items.convertBlockToItem(block);
            breakBlock(block, true);
            if (dropItems) {
                Worlds.dropItem(block.getLocation(), drop);
            }

			/*
            For all the blocks surrounding the parent, we're going to continue breaking the
			blocks (logs, and potentially leaves) until they're all gone!
			 */
            getBlocksSurrounding(block).forEach(b -> {
                breakTree(b, leaves, dropItems);
            });
        }
    }

    /**
     * Schedule a block to be regenerated!
     *
     * @param block  block to regenerate.
     * @param effect whether or not to play the restoration effect.
     */
    public static void scheduleBlockRegen(Block block, boolean effect) {
        scheduleBlockRegen(block, effect, BLOCK_REGEN_DELAY);
    }

    /**
     * Schedule a block to be regenerated after a delay.
     *
     * @param block  block to restore.
     * @param effect whether or not to play the effect when restoring the block
     * @param delay  delay before regenerating the block (in ticks)
     */
    public static void scheduleBlockRegen(Block block, boolean effect, long delay) {
        BlockData blockData = new BlockData(block);
        Commons.getInstance().getThreadManager().runTaskLater(new BlockRegenThread(blockData, effect), delay);
    }

    /**
     * Schedule a list of blocks to be regenerated, after a specific amount of seconds.
     *
     * @param blocks       List of blocks to regenerate
     * @param effect       whether or not to play the restoration effect when restoring the blocks.
     * @param secondsDelay delay in seconds before restoring the blocks.
     */
    public static void scheduleBlockRegen(List<Block> blocks, boolean effect, int secondsDelay) {
        Commons.getInstance().getThreadManager().runTaskLater(new BlocksRegenThread(blocks, effect), TimeHandler.getTimeInTicks(secondsDelay, TimeType.SECOND));
    }

    /**
     * Changes the block to the data contained by {@param blockData}
     *
     * @param block     block to change
     * @param blockData material data used to update the block
     */
    public static void setBlock(Block block, MaterialData blockData) {
        //Update the blocks material data
        block.getState().setData(blockData);
        //Update the type
        block.setType(blockData.getItemType());
        //Update the byte-data (Positioning, etc)
        block.setData(block.getData());
        //Update the block state
        block.getState().update(true);
    }

    /**
     * Change the block at the given location to the data contained by {@param data}
     *
     * @param loc  location of the block to modify.
     * @param data materialdata to assign to the block.
     */
    public static void setBlock(Location loc, MaterialData data) {
        setBlock(loc.getBlock(), data);
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
     * Change the material of a block at a specific location.
     *
     * @param location location to change the block at.
     * @param material material to assign to the block
     */
    public static void setBlock(Location location, Material material) {
        setBlock(getBlockAt(location), material);
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
     * Check if a block's type matches any of those passed by the types parameter.
     *
     * @param block block to check.
     * @param types type(s) to match against.
     * @return
     */
    public static boolean isOfAnyType(Block block, Material... types) {
        Set<Material> mats = Sets.newHashSet(types);
        return mats.contains(block.getType());
    }

    /**
     * Check a block at a specific XYZ Cordinate for a world, and destroy all the blocks with the
     * id defined by <i>required</i> around the block
     *
     * @param world   World which we're getting the block
     * @param x       x-Axis coordinate for the blocks location
     * @param y       y-Axis coordinate for the blocks location
     * @param z       z-Axis coordinate for the blocks location
     * @param blockId id of the block to destroy
     */
    private void checkAndDestroyAround(World world, int x, int y, int z, int blockId) {
        checkAndDestroy(world, x, y, z + 1, blockId);
        checkAndDestroy(world, x, y, z - 1, blockId);
        checkAndDestroy(world, x, y + 1, z, blockId);
        checkAndDestroy(world, x, y - 1, z, blockId);
        checkAndDestroy(world, x + 1, y, z, blockId);
        checkAndDestroy(world, x - 1, y, z, blockId);
    }

    /**
     * Check if a block at a specific location is the same as the block defined by <i>required</i>
     * and then destroy the block if so
     *
     * @param world   World which we're getting the block
     * @param x       x-Axis coordinate for the blocks location
     * @param y       y-Axis coordinate for the blocks location
     * @param z       z-Axis coordinate for the blocks location
     * @param blockId id of the block to destroy
     */
    private void checkAndDestroy(World world, int x, int y, int z, int blockId) {
        if (world.getBlockTypeIdAt(x, y, z) == blockId) {
            world.getBlockAt(x, y, z).setTypeId(BlockID.AIR);
        }
    }

    /**
     * Spawn primed tnt at a specific location
     *
     * @param location location to spawn tnt
     */
    public static TNTPrimed spawnTNT(Location location) {
        return (TNTPrimed) location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
    }

    /**
     * Spawn a specific amount of primed tnt at a specific location
     *
     * @param location location to spawn tnt
     * @param amount   amount of tnt to spawn
     */
    public static void spawnTNT(Location location, int amount) {
        for (int i = 0; i < amount; i++) {
            spawnTNT(location);
        }
    }

    /**
     * Get the block relative to the desired block face.
     *
     * @param parent base-block used to retrieve the relative block.
     * @param face   face to retrieve the block at.
     * @return block attached to the desired face of the parent.
     */
    public static Block getBlockFacing(Block parent, BlockFace face) {
        return parent.getRelative(face);
    }

    /**
     * Retrieve all the blocks that surround the parent block in all possible directions.
     *
     * @param parent parent block to retrieve the surrounding blocks from.
     * @return a {@link java.util.HashSet} of all the {@link org.bukkit.block.Block} surrounding the parent block.
     */
    public static Set<Block> getBlocksSurrounding(Block parent) {
        return EnumSet.allOf(BlockFace.class).stream().map(face -> getBlockFacing(parent, face)).collect(Collectors.toSet());
    }

    /**
     * Gets the block above the block passed
     *
     * @param block block to get the block above
     * @return block that was above the previous block (may be null if block didn't exist)
     */
    public static Block getBlockAbove(Block block) {
        int[] xyz = Locations.getXYZ(block.getLocation());
        return block.getWorld().getBlockAt(xyz[0], xyz[1] - 1, xyz[2]);
    }

    /**
     * Get a block below the block passed
     *
     * @param block block to get the block below
     * @return block that was below the previous block (may be null if block did not exist)
     */
    public static Block getBlockBelow(Block block) {
        int[] xyz = Locations.getXYZ(block.getLocation());
        return block.getWorld().getBlockAt(xyz[0], xyz[1] + 1, xyz[2]);
    }

    /**
     * Retrieve the nearest empty space in a given radius around the base block.
     *
     * @param b         block to use as a base for the search.
     * @param maxradius maximum radius around the block to search for an empty space in.
     * @return the nearest empty space if available, otherwise null if none is present.
     */
    public static Block getNearestEmptySpace(Block b, int maxradius) {
        BlockFace[] faces = {BlockFace.UP, BlockFace.NORTH, BlockFace.EAST};
        BlockFace[][] orth = {{BlockFace.NORTH, BlockFace.EAST}, {BlockFace.UP, BlockFace.EAST}, {BlockFace.NORTH, BlockFace.UP}};
        for (int r = 0; r <= maxradius; r++) {
            for (int s = 0; s < 6; s++) {
                BlockFace f = faces[s % 3];
                BlockFace[] o = orth[s % 3];
                if (s >= 3) {
                    f = f.getOppositeFace();
                }
                Block c = b.getRelative(f, r);
                for (int x = -r; x <= r; x++) {
                    for (int y = -r; y <= r; y++) {
                        Block a = c.getRelative(o[0], x).getRelative(o[1], y);
                        if (a.getTypeId() == 0 && a.getRelative(BlockFace.UP).getTypeId() == 0) {
                            return a;
                        }
                    }
                }
            }
        }
        return null;// no empty space within a cube of (2*(maxradius+1))^3
    }

    /**
     * Ouuuuu! Set the world on fiiiire!
     * Collection of locations to set on fire.
     *
     * @param locations places to be set on fire.
     */
    public static void setFire(Collection<Location> locations) {
        locations.forEach(l -> Blocks.setBlock(l, Material.FIRE));
    }

    /**
     * For every entry in the map, set the location (key) to the material specified by the corresponding value.
     *
     * @param materialLocations map of the locations and their corresponding materials.
     */
    public static void restoreBlocks(Map<Location, Material> materialLocations) {
        materialLocations.forEach(Blocks::setBlock);
    }


    /**
     * Spawn a {@link org.bukkit.entity.FallingBlock} of the given material type
     *
     * @param loc      location to spawn the block at
     * @param material material to make the block
     * @return the falling block that was spawned
     */
    public static FallingBlock spawnFallingBlock(Location loc, Material material) {
        return spawnFallingBlock(loc, material, 0);
    }

    /**
     * Spawn a {@link org.bukkit.entity.FallingBlock} of the given material at the location specified.
     *
     * @param loc       location to spawn the falling block at
     * @param material  material the block will be.
     * @param dataValue data value to give the material
     * @return the falling block that was spawned.
     */
    public static FallingBlock spawnFallingBlock(Location loc, Material material, int dataValue) {
        return spawnFallingBlock(loc, Items.getMaterialData(material, dataValue));
    }

    /**
     * Spawn a {@link org.bukkit.entity.FallingBlock} with the specified materialdata at the given location
     *
     * @param loc  location to spawn the fallingblock
     * @param data materialdata the block has when spawned.
     * @return the falling block that was spawned.
     */
    public static FallingBlock spawnFallingBlock(Location loc, MaterialData data) {
        World world = loc.getWorld();
        FallingBlock block = world.spawnFallingBlock(loc, data.getItemType(), data.getData());
        return block;
    }

    private static final Set<Material> GRASS_BLACKLIST = Sets.newHashSet(
            Material.SAPLING,
            Material.LONG_GRASS,
            Material.DEAD_BUSH,
            Material.YELLOW_FLOWER,
            Material.RED_ROSE,
            Material.RED_MUSHROOM,
            Material.BROWN_MUSHROOM,
            Material.CACTUS,
            Material.VINE
    );

    private static final Set<Material> GRASS_WHITELIST = Sets.newHashSet(
            Material.GRASS,
            Material.DIRT,
            Material.SOUL_SAND
    );

    private static final List<ChancedBlock> GRASS_PATCH_BLOCKS = Lists.newArrayList(
            ChancedBlock.of(31, 1, 100),
            ChancedBlock.of(31, 2, 45),
            ChancedBlock.of(37, 0, 5),
            ChancedBlock.of(38, 0, 5),
            ChancedBlock.of(86, 0, 2),
            ChancedBlock.of(103, 0, 2),
            ChancedBlock.of(38, 1, 2),
            ChancedBlock.of(38, 2, 2),
            ChancedBlock.of(38, 3, 2),
            ChancedBlock.of(38, 4, 2),
            ChancedBlock.of(38, 5, 2),
            ChancedBlock.of(38, 6, 2),
            ChancedBlock.of(38, 7, 2),
            ChancedBlock.of(38, 8, 2),
            ChancedBlock.of(39, 0, 1),
            ChancedBlock.of(40, 0, 1),
            ChancedBlock.of(175, 0, 5),
            ChancedBlock.of(175, 1, 5),
            ChancedBlock.of(175, 2, 10),
            ChancedBlock.of(175, 3, 10),
            ChancedBlock.of(175, 4, 5),
            ChancedBlock.of(175, 5, 5)
    );


    /**
     * Simulate the growth of grass & flowers; Like bonemeal!
     *
     * @param loc     center of where the growth should begin.
     * @param radius  radius around the center to grow the grass in
     * @param density Value of 1 - 100, with 1 being low density, and 100 being maximum density; Used to make the patches of grass thicker or thinner.
     */
    public static void regrowGrass(Location loc, int radius, int density) {
        if (density > 100) {
            density = 100;
        }

        Block handle;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                handle = loc.getWorld().getHighestBlockAt((int) loc.getX() + x, (int) loc.getZ() + z);

                if (!Locations.isInRadius(loc, handle.getLocation(), radius)) {
                    continue;
                }

                Block downFace = getBlockFacing(handle, BlockFace.DOWN);

                if (!GRASS_WHITELIST.contains(downFace.getType())) {
                    continue;
                }

                Material baseMat = handle.getType();

                if (GRASS_BLACKLIST.contains(baseMat)) {
                    continue;
                }

                if (!NumberUtil.percentCheck(density)) {
                    continue;
                }

                ChancedBlock replacementBlock = null;

                while (true) {
                    replacementBlock = ListUtils.getRandom(GRASS_PATCH_BLOCKS);

                    if (replacementBlock.pass()) {
                        break;
                    }
                }

//				Chat.debug("Replacing " + baseMat.name() + " with " + replacementBlock.getData().getItemType().name() + ":" + replacementBlock.getData().getData());
                handle.setType(replacementBlock.getMaterial());
                handle.setData(replacementBlock.getData());

                //todo implement second check for flowers
            }
        }
//		//todo implement size check for potential brushes?
    }

    private static double getDistance(Location pos1, Location pos2) {
        double ySize = pos1.getY() - pos2.getY();
        double zSize = pos1.getZ() - pos2.getZ();
        double xSize = pos1.getX() - pos2.getX();

        double distance = Math.sqrt((xSize * xSize) + (ySize * ySize) + (zSize + zSize));
        return distance;
    }
}
