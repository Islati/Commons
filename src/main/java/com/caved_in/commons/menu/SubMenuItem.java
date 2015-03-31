package com.caved_in.commons.menu;

import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class SubMenuItem extends MenuItem {
	private ItemMenu itemMenu;

	public SubMenuItem(ItemMenu itemMenu) {
		this.itemMenu = itemMenu;
	}

	public SubMenuItem(String text, ItemMenu itemMenu) {
		super(text);
		this.itemMenu = itemMenu;
	}

	public SubMenuItem(String text, MaterialData icon, ItemMenu itemMenu) {
		super(text, icon);
		this.itemMenu = itemMenu;
	}

	public SubMenuItem(String text, MaterialData icon, int number, ItemMenu itemMenu) {
		super(text, icon, number);
		this.itemMenu = itemMenu;
	}

	public ItemMenu getSubMenu() {
		return itemMenu;
	}

	@Override
	public void onClick(Player player) {
		getMenu().switchMenu(player, itemMenu);
	}
}
