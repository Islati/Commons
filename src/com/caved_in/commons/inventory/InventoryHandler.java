package com.caved_in.commons.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class InventoryHandler
{
	public static boolean isChest(Inventory inventory)
	{
		return inventory.getType() == InventoryType.CHEST || inventory.getType() == InventoryType.ENDER_CHEST;
	}

	public static List<ItemStack> getContents(Inventory inventory)
	{
		return Arrays.asList(inventory.getContents());
	}

	public static boolean isEmpty(Inventory inventory)
	{
		for(ItemStack itemStack : inventory.getContents())
		{
			if (itemStack != null)
			{
				return false;
			}
		}
		return true;
	}

	public static void setContents(Inventory inventory, ItemStack[] items)
	{
		inventory.setContents(items);
	}
}
