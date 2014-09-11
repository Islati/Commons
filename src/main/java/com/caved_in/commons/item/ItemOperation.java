package com.caved_in.commons.item;

import org.bukkit.inventory.ItemStack;

public interface ItemOperation {

	/**
	 * Performs an operation on an item, and returns the item after being modified.
	 *
	 * @param item item to perform the operation on.
	 * @return item after the operation was performed
	 */
	public ItemStack operate(ItemStack item);
}
