package com.caved_in.commons.game.gadget;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ItemGadget implements Gadget {

	private boolean droppable;

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

	public boolean isDroppable() {
		return droppable;
	}

	public void setDroppable(boolean canDrop) {
		droppable = canDrop;
	}

	public void giveTo(Player player) {
		Players.giveItem(player, getItem());
	}

	public boolean drop(Player p) {
		onDrop(p);
		return isDroppable();
	}

	public abstract int id();

	public abstract void perform(Player holder);

	public void onDrop(Player p) {

	}
}
