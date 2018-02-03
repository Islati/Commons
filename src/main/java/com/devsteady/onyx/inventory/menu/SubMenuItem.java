package com.devsteady.onyx.inventory.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.material.MaterialData;

public class SubMenuItem extends MenuItem {
	private Menu menu;

	public SubMenuItem(Menu menu) {
		this.menu = menu;
	}

	public SubMenuItem(String text, Menu menu) {
		super(text);
		this.menu = menu;
	}

	public SubMenuItem(String text, MaterialData icon, Menu menu) {
		super(text, icon);
		this.menu = menu;
	}

	public SubMenuItem(String text, MaterialData icon, int number, Menu menu) {
		super(text, icon, number);
		this.menu = menu;
	}

	public Menu getSubMenu() {
		return menu;
	}

	@Override
	public void onClick(Player player, ClickType type) {
		getMenu().switchMenu(player, menu);
	}
}
