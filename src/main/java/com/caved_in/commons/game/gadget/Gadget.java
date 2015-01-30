package com.caved_in.commons.game.gadget;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface Gadget extends Listener {

	/**
	 * @return The itemstack that represents this gadget
	 */
	public ItemStack getItem();

	/**
	 * Will take place whenever the performs an interaction with the gadget.
	 * @param holder player using the gadget.
	 */
	public void perform(Player holder);

	default public void onBreak(Player p) {

	}

	default public void onDrop(Player player, Item item) {

	}

	public <T extends GadgetProperties> T properties();

	public int id();
}
