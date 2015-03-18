package com.caved_in.commons.game.gadget;

import com.caved_in.commons.item.Items;
import lombok.ToString;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "gadget-properties")
@ToString(of = {"durability", "isBreakable", "isDroppable"})
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

	public GadgetProperties() {

	}

	public GadgetProperties(@Element(name = "durability") int durability, @Element(name = "breakable") boolean isBreakable, @Element(name = "droppable") boolean isDroppable) {
		this.durability = durability;
		this.isBreakable = isBreakable;
		this.isDroppable = isDroppable;
	}

	public GadgetProperties breakable(boolean canBreak) {
		this.isBreakable = canBreak;
		return this;
	}

	public GadgetProperties droppable(boolean canDrop) {
		this.isDroppable = canDrop;
		return this;
	}

	public GadgetProperties durability(int uses) {
		this.durability = uses;
		return this;
	}

	public GadgetProperties durability(ItemStack item) {
		if (Items.isWeapon(item) || Items.isTool(item)) {
			return durability(item.getDurability());
		} else {
			durability = -1;
		}

		return this;
	}

	public boolean isBreakable() {
		return isBreakable || durability == -1;
	}

	public boolean isDroppable() {
		return isDroppable;
	}

	public int getDurability() {
		return durability;
	}
}
