package com.caved_in.commons.inventory;

import com.caved_in.commons.config.XmlItemStack;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

@Root(name = "Hotbar")
public class Hotbar {
	@ElementArray(name = "items", entry = "item", required = false)
	private XmlItemStack[] items = new XmlItemStack[8];

	private ItemStack[] stacks = null;

	public Hotbar(@ElementArray(name = "items", entry = "item", required = false) XmlItemStack[] items) {
		this.items = items;
		getItems();
	}

	public Hotbar(ItemStack... items) {
		stacks = new ItemStack[8];
		for (int i = 0; i < items.length; i++) {
			ItemStack item = items[i];
			if (item == null) {
				this.items[i] = null;
				continue;
			}

			this.items[i] = XmlItemStack.fromItem(item);
			stacks[i] = this.items[i].getItemStack();
		}
	}

	/**
	 * Assign a slot (1 to 9) an item.
	 *
	 * @param slot slot to set the item in (1 to 9)
	 * @param item item to set in the slot.
	 * @return the hotbar instance.
	 */
	public Hotbar set(int slot, ItemStack item) {
		items[slot - 1] = XmlItemStack.fromItem(item);
		return this;
	}

	/**
	 * Assign the players hotbar the contents of this hotbar.
	 *
	 * @param player player to change the hotbar contents of
	 */
	public void assign(Player player) {
		for (int i = 0; i < getItems().length; i++) {
			Players.setHotbarItem(player, items[i].getItemStack(), i);
		}
	}

	/**
	 * @return an array of all the items in the hotbar.
	 */
	public ItemStack[] getItems() {
		if (stacks == null) {
			stacks = new ItemStack[8];
			for (int i = 0; i < items.length; i++) {
				stacks[i] = items[i].getItemStack();
			}
		}

		return stacks;
	}
}
