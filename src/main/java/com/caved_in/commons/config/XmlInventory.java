package com.caved_in.commons.config;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.HashMap;
import java.util.Map;

@Root(name = "inventory")
public class XmlInventory {
	@ElementMap(name = "items", key = "slot", value = "item", valueType = XmlItemStack.class)
	private Map<Integer, XmlItemStack> inventoryItems = new HashMap<>();

	public XmlInventory(@ElementMap(name = "items", key = "slot", value = "item", valueType = XmlItemStack.class) Map<Integer, XmlItemStack> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	public XmlInventory(Inventory inventory) {
		ItemStack[] contents = inventory.getContents();
		for (int i = 0; i < contents.length; i++) {
			ItemStack item = contents[i];
			if (item == null) {
				continue;
			}

			inventoryItems.put(i, XmlItemStack.fromItem(item));
		}
	}

	public Map<Integer, ItemStack> getInventoryContents() {
		Map<Integer, ItemStack> inventoryContents = new HashMap<>();
		inventoryItems.entrySet().forEach(e -> inventoryContents.put(e.getKey(), e.getValue().getItemStack()));
		return inventoryContents;
	}
}
