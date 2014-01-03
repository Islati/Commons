package com.caved_in.commons.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WrappedLore {
	private Map<Integer, String> itemLore;

	public WrappedLore(ItemStack itemStack) {
		initializeLoreMap(ItemHandler.getItemLore(itemStack));
	}

	public WrappedLore(String... itemLore) {
		initializeLoreMap(Arrays.asList(itemLore));
	}

	public WrappedLore(ArrayList<String> itemLore) {
		initializeLoreMap(itemLore);
	}

	private void initializeLoreMap(List<String> itemLore) {
		for(int i = 0; i < itemLore.size(); i++) {
			this.itemLore.put(i,itemLore.get(i));
		}
	}

	public int getLoreAmount() {
		return itemLore.size();
	}

	public String getLoreAt(int index) {
		return itemLore.get(index);
	}

	public List<String> getLore() {
		return new ArrayList<String>(itemLore.values());
	}
}
