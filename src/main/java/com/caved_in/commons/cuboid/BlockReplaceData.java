package com.caved_in.commons.cuboid;

import org.bukkit.Material;

/**
 * Holds a search material, replace material, and chance value for use in cuboid manipulation.
 */
public class BlockReplaceData {
    private int chance = 100;
    private Material search;
    private Material replace;

    /**
     * Create a new BlockReplaceData instance.
     *
     * @param search  material to find
     * @param replace material to replace the searched materials with.
     */
    public BlockReplaceData(Material search, Material replace) {
        this.search = search;
        this.replace = replace;
    }

    /**
     * Set the chance of blocks being replaced.
     *
     * @param chance chance that the searched type will get replaced (out of 100)
     * @return the BlockReplaceData instance
     */
    public BlockReplaceData chance(int chance) {
        this.chance = chance;
        return this;
    }

    /**
     * Set the material being searched.
     *
     * @param search material to find.
     * @return the BlockReplaceData instance.
     */
    public BlockReplaceData search(Material search) {
        this.search = search;
        return this;
    }

    /**
     * Set the material that's replacing the searched material.
     *
     * @param replace material to replace the searched materials with.
     * @return the BlockReplaceData instance
     */
    public BlockReplaceData replace(Material replace) {
        this.replace = replace;
        return this;
    }

    /**
     * @return the chance (out of 100) for the block to be replaced
     */
    public int chance() {
        return this.chance;
    }

    /**
     * @return the material to search for.
     */
    public Material search() {
        return search;
    }

    /**
     * @return the material to take place of the searched material.
     */
    public Material replace() {
        return replace;
    }
}
