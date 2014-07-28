package com.caved_in.commons.plugin.game.gadget;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ItemGadget implements Gadget {

	private ItemStack gadgetItem;

	public ItemGadget() {

	}

	public ItemGadget(ItemStack item) {
		this.gadgetItem = item.clone();
	}

	public ItemStack getItem() {
		return gadgetItem;
	}

	public void setItem(ItemStack item) {
		this.gadgetItem = item;
	}

	public void giveTo(Player player) {
		Players.giveItem(player, getItem());
	}

	public abstract void perform(Player holder);

	public abstract int id();
}
