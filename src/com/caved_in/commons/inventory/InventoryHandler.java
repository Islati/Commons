package com.caved_in.commons.inventory;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.EnderChest;

import java.util.Arrays;
import java.util.List;

public class InventoryHandler {
	/**
	 * Check if the type of an inventory is a chest
	 * @param inventory inventory to check
	 * @return true if the inventories type is a chest, or enderchest
	 */
	public static boolean isChest(Inventory inventory) {
		return inventory.getType() == InventoryType.CHEST || inventory.getType() == InventoryType.ENDER_CHEST;
	}

	/**
	 * Check if the inventoryholder is a chest
	 * @param inventoryHolder inventoryholder to check
	 * @return true if the inventorys holder is a chest, double chest, or enderchest; False otherwise
	 */
	public static boolean isChest(InventoryHolder inventoryHolder) {
		return inventoryHolder instanceof Chest || inventoryHolder instanceof DoubleChest || inventoryHolder.getInventory().getType() == InventoryType.ENDER_CHEST;
	}

	/**
	 * Gets the chest block related to the passed inventory holder
	 * @param inventoryHolder inventoryholder to get the chest of
	 * @return
	 */
	public static Chest getChestFromHolder(InventoryHolder inventoryHolder) {
		if (isChest(inventoryHolder)) {
			return (Chest)inventoryHolder;
		}
		return null;
	}

	public static List<ItemStack> getContents(Inventory inventory) {
		return Arrays.asList(inventory.getContents());
	}

	public static boolean isEmpty(Inventory inventory) {
		for (ItemStack itemStack : inventory.getContents()) {
			if (itemStack != null) {
				return false;
			}
		}
		return true;
	}

	public static void setContents(Inventory inventory, ItemStack[] items) {
		inventory.setContents(items);
	}
}
