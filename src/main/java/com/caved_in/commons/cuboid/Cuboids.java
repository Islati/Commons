package com.caved_in.commons.cuboid;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;

public class Cuboids {
    /**
     * Search for a material within a region and replace it with the specified material
     *
     * @param area    area to operate within
     * @param search  the material that's being replaced
     * @param replace the material to replace with
     */
    public static void replace(Cuboid area, Material search, Material replace) {
        //For every block that matches the searched type, replace it with the given replacement
        area.stream().filter(b -> b.getType() == search).forEach(b -> Blocks.setBlock(b, replace));
    }

    /**
     * Search for a material within a region and replace a percent of that material with the replacement material.
     *
     * @param area    area to operate within
     * @param search  the material that's being replaced
     * @param replace the material to replace with
     * @param chance  chance to change the blocks material (out of 100)
     */
    public static void replace(Cuboid area, Material search, Material replace, int chance) {
        area.stream().filter(b -> b.getType() == search).forEach(b -> {
            if (NumberUtil.percentCheck(chance)) {
                Blocks.setBlock(b, replace);
            }
        });
    }

    /**
     * Search for a material within a region and replace a percent of that material with the data given.
     *
     * @param area         area to operate within
     * @param replacements material search & replace data.
     */
    public static void replace(Cuboid area, BlockReplaceData... replacements) {
        for (BlockReplaceData data : replacements) {
            replace(area, data.search(), data.replace(), data.chance());
        }
    }

    /**
     * Remove all the blocks matching the given type from within a cuboid area.
     *
     * @param area     area to operate within
     * @param material material to remove.
     */
    public static void remove(Cuboid area, Material material) {
        area.stream().filter(b -> b.getType() == material).forEach(b -> Blocks.setBlock(b, Material.AIR));
    }

    //todo implement method to replace blocks of a specific type, with another type / list of types with chance
}
