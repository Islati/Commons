package com.caved_in.commons.inventory;

import com.caved_in.commons.block.chest.ChestType;
import com.caved_in.commons.item.Items;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventories {

	public static ChestType getChestType(InventoryHolder inventoryHolder) {
		if (isChest(inventoryHolder)) {
			return ChestType.SINGLE_CHEST;
		} else if (isDoubleChest(inventoryHolder)) {
			return ChestType.DOUBLE_CHEST;
		} else if (inventoryHolder.getInventory().getType() == InventoryType.ENDER_CHEST) {
			return ChestType.ENDER_CHEST;
		} else {
			return ChestType.UNKNOWN;
		}
	}

	/**
	 * Check if the type of an inventory is a chest
	 *
	 * @param inventory inventory to check
	 * @return true if the inventories type is a chest
	 */
	public static boolean isChest(Inventory inventory) {
		return isChest(inventory.getHolder());
	}

	/**
	 * Check if the inventoryholder is a chest
	 *
	 * @param inventoryHolder inventoryholder to check
	 * @return true if the inventorys holder is a chest
	 */
	public static boolean isChest(InventoryHolder inventoryHolder) {
		ChestType chestType = getChestType(inventoryHolder);
		switch (chestType) {
			case SINGLE_CHEST:
			case DOUBLE_CHEST:
			case ENDER_CHEST:
				return true;
			default:
				return false;
		}
	}

	public static boolean isSingleChest(InventoryHolder inventoryHolder) {
		return getChestType(inventoryHolder) == ChestType.SINGLE_CHEST;
	}

	/**
	 * Check if the inventoryholder is a double chest
	 *
	 * @param inventoryHolder inventoryholder to check
	 * @return true if the inventorys holder is a double chest
	 */
	public static boolean isDoubleChest(InventoryHolder inventoryHolder) {
		return getChestType(inventoryHolder) == ChestType.DOUBLE_CHEST;
	}

	/**
	 * Gets the chest block related to the passed inventory holder
	 *
	 * @param inventoryHolder inventoryholder to get the chest of
	 * @return
	 */
	public static Chest getChest(InventoryHolder inventoryHolder) {
		return (Chest) inventoryHolder;
	}

	public static DoubleChest getDoubleChest(InventoryHolder inventoryHolder) {
		return (DoubleChest) inventoryHolder;
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

	public static void setItem(Inventory inventory, int slot, ItemStack item) {
		inventory.setItem(slot, item);
	}

	public static InventoryView openWorkbench(Player player) {
		return player.openWorkbench(null, true);
	}

	public static void setViewItemAtSlot(InventoryView inventoryView, int itemSlot, ItemStack itemStack) {
		inventoryView.setItem(itemSlot, itemStack);
	}

	public static void setViewItems(InventoryView inventoryView, Map<Integer, ItemStack> inventoryItems) {
		for (Map.Entry<Integer, ItemStack> itemEntry : inventoryItems.entrySet()) {
			inventoryView.setItem(itemEntry.getKey(), itemEntry.getValue());
		}
	}

	public static boolean contains(Inventory inventory, Material material, String itemName) {
		HashMap<Integer, ? extends ItemStack> items = inventory.all(material);
		for (Map.Entry<Integer, ? extends ItemStack> inventoryItem : items.entrySet()) {
			if (Items.nameContains(inventoryItem.getValue(), itemName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean contains(Inventory inventory, ItemStack itemStack) {
		Map<Integer, ? extends ItemStack> items = inventory.all(itemStack);
		for (Map.Entry<Integer, ? extends ItemStack> item : items.entrySet()) {
			if (item.getValue().isSimilar(itemStack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean contains(Inventory inventory, Material material) {
		return inventory.contains(material);
	}

	public static int getRows(int ItemCount) {
		return ((int) Math.ceil(ItemCount / 9.0D));
	}

}
