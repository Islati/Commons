package com.caved_in.commons.block;

import com.caved_in.commons.item.Items;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;

/**
 * Wrapped data of a material, datavalue, and chance- Used internally with grass-regrowth, can also be used to place blocks with a chance.
 */
public class ChancedBlock {
    private Material mat;
    private byte data;
    private int chance;

    /**
     * Create a new instance of ChancedBlock.
     *
     * @param mat    material to give the chanced block
     * @param id     data value to attach to the material.
     * @param chance chance the block has to spawn / place / pass.
     * @return ChancedBlock with the material, id and chance assigned.
     */
    public static ChancedBlock of(Material mat, int id, int chance) {
        return new ChancedBlock(mat, id, chance);
    }

    /**
     * Create a new instance of ChancedBlock using an items id.
     *
     * @param id     id of the material to use
     * @param data   data value to attach to the material
     * @param chance chance the block has to spawn / place / pass
     * @return ChancedBlock with the material, id, and chance assigned.
     */
    public static ChancedBlock of(int id, int data, int chance) {
        return new ChancedBlock(id, data, chance);
    }

    public ChancedBlock() {

    }

    public ChancedBlock(int blockId, int data, int chance) {
        this(Items.getMaterialById(blockId), data, chance);
    }

    public ChancedBlock(Material material, int data, int chance) {
        this.mat = material;
        this.data = (byte) data;
        this.chance = chance;
    }

    public ChancedBlock data(int data) {
        this.data = (byte) data;
        return this;
    }

    public ChancedBlock data(Material mat, int data) {
        this.mat = mat;
        return data(data);
    }

    public ChancedBlock chance(int chance) {
        this.chance = chance;
        return this;
    }

    public boolean pass() {
        return NumberUtil.percentCheck(chance);
    }

    public Material getMaterial() {
        return mat;
    }

    public byte getData() {
        return data;
    }
}
