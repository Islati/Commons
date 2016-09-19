package com.caved_in.commons.game.gadget;

import com.caved_in.commons.inventory.HandSlot;
import com.caved_in.commons.item.Items;
import lombok.ToString;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "gadget-properties")
@ToString(of = {"durability", "isBreakable", "isDroppable"})
/**
 * All the properties specific to Gadgets, accessed through any Gadget instance, it manages properties specific
 * to each gadget instance.
 */
public class GadgetProperties {
    /*
    The durability of the item (How many uses it has)
     */
    @Element(name = "durability")
    private int durability;

    /*
    Whether or not the item is breakable; Has no default value.
     */
    @Element(name = "breakable")
    private boolean isBreakable;

    /*

    Whether or not the item can be dropped; by default it's false.
     */
    @Element(name = "droppable")
    private boolean isDroppable = false;

    @Element(name = "mainHandEquippable")
    private boolean mainHandEquipable = true;

    @Element(name = "offHandEquippable")
    private boolean offHandEquipable = true;

    public GadgetProperties() {

    }

    public GadgetProperties(@Element(name = "durability") int durability, @Element(name = "breakable") boolean isBreakable, @Element(name = "droppable") boolean isDroppable, @Element(name = "mainHandEquipable") boolean mainHandEquipable, @Element(name = "offHandEquipable") boolean offHandEquipable) {
        this.durability = durability;
        this.isBreakable = isBreakable;
        this.isDroppable = isDroppable;
        this.mainHandEquipable = mainHandEquipable;
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
     * Assign the durability of the item to that of the given item stack.
     *
     * @param item item to clone the durability from.
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

    public GadgetProperties mainHandEquippable(boolean equip) {
        this.mainHandEquipable = equip;
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
     * Check whether or not the gadget can be equipped in the given slot.
     *
     * @param slot slot to check if the gadget can be equipped in.
     * @return true if the gadget can be equipped in the given slot, false otherwise.
     */
    public boolean isEquippable(HandSlot slot) {
        switch (slot) {
            case MAIN_HAND:
                return mainHandEquipable;
            case OFF_HAND:
                return offHandEquipable;
            default:
                return false;
        }
    }
}
