package com.caved_in.commons.game.gadget;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ItemGadget implements Gadget {

	private ItemStack gadgetItem;

	private GadgetProperties properties = new GadgetProperties();

	public ItemGadget() {

	}

	public ItemGadget(ItemStack item) {
		setItem(item);
	}

	public ItemStack getItem() {
		return gadgetItem;
	}

	public void setItem(ItemStack item) {
		this.gadgetItem = item.clone();
		properties.durability(item);
	}

	public void giveTo(Player player) {
		Players.giveItem(player, getItem());
	}

	public abstract int id();

	public abstract void perform(Player holder);

	public void onDrop(Player player, Item dropped) {

	}

	@Override
	public GadgetProperties properties() {
		return properties;
	}


}
