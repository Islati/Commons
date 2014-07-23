package com.caved_in.commons.gadget;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface Gadget extends Listener {

	public ItemStack getItem();

	public void perform(Player holder);

	public int id();
}
