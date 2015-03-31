package com.caved_in.commons.game.gadget;

import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.menu.ItemMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class MenuGadget extends ItemGadget {

	private ItemMenu menu;
	private int id;

	public MenuGadget(ItemBuilder builder, ItemMenu menu, int id) {
		super(builder);
		this.menu = menu;
		this.id = id;
	}

	public MenuGadget(ItemStack item, ItemMenu menu, int id) {
		super(item);
		this.menu = menu;
		this.id = id;
	}

	@Override
	public void perform(Player holder) {
		menu.openMenu(holder);
	}

	@Override
	public int id() {
		return id;
	}
}
