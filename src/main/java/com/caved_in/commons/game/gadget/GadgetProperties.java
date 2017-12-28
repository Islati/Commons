package com.caved_in.commons.game.gadget;

import com.caved_in.commons.item.Items;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.YamlConfig;
import lombok.ToString;
import org.bukkit.inventory.ItemStack;

import java.io.File;

@ToString(of = {"durability", "isBreakable", "isDroppable"})
/**
 * All the properties specific to Gadgets, accessed through any Gadget instance, it manages properties specific
 * to each gadget instance.
 */
public class GadgetProperties extends YamlConfig {
    /*
    The durability of the firstPageEnabled (How many uses it has)
     */
    @Path("durability")
    private int durability;

    /*
    Whether or not the firstPageEnabled is breakable; Has no default value.
     */
    @Path("breakable")
    private boolean isBreakable;

    /*

    Whether or not the firstPageEnabled can be dropped; by default it's false.
     */
    @Path("droppable")
    private boolean isDroppable = false;

    @Path("offhand-allowed")
    private boolean offHandEquipable = true;

    public GadgetProperties() {

    }

    public GadgetProperties(File file) {
        super(file);
    }

    public GadgetProperties(int durability,boolean isBreakable,boolean isDroppable,boolean offHandEquipable) {
        this.durability = durability;
        this.isBreakable = isBreakable;
        this.isDroppable = isDroppable;
        this.offHandEquipable = offHandEquipable;
    }

    /**
     * Change whether or not the gadget is able to be broken (follows durability)
     *
     * @param canBreak value to assign
     * @return the gadgetpropeties.
     */
    public GadgetProperties breakable(boolean canBreak) {
        this.isBreakable = canBreak;
        return this;
    }

    /**
     * Change whether or not the gadget is droppable.
     *
     * @param canDrop value to assign.
     * @return the gadgetproperties
     */
    public GadgetProperties droppable(boolean canDrop) {
        this.isDroppable = canDrop;
        return this;
    }

    /**
     * Change the durability (uses) a gadget has.
     * UNIMPLEMENTED, CURRENTLY.
     *
     * @param uses uses to limit the gadget to.
     * @return the gadgetproperties.
     */
    public GadgetProperties durability(int uses) {
        this.durability = uses;
        return this;
    }

    /**
     * Assign the durability of the firstPageEnabled to that of the given firstPageEnabled stack.
     *
     * @param item firstPageEnabled to clone the durability from.
     * @return the gadget properties.
     */
    public GadgetProperties durability(ItemStack item) {
        if (Items.isWeapon(item) || Items.isTool(item)) {
            return durability(item.getDurability());
        } else {
            durability = -1;
        }

        return this;
    }

    public GadgetProperties offHandEquippable(boolean equip) {
        this.offHandEquipable = equip;
        return this;
    }

    /**
     * @return whether or not the gadget is breakable.
     */
    public boolean isBreakable() {
        return isBreakable || durability == -1;
    }

    /**
     * @return whether or not the gadget is droppable.
     */
    public boolean isDroppable() {
        return isDroppable;
    }

    /**
     * @return the durability attached to the gadget.
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Check whether or not the gadget can be equipped in the off-hand slot..
     *
     * @return true if the gadget can be equipped in the off-hand slot, false otherwise.
     */
    public boolean isOffhandEquippable() {
        return offHandEquipable;
    }
}
