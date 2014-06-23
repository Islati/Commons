package com.caved_in.commons.menu.menus.configmenu.items;

import com.caved_in.commons.item.Items;
import com.caved_in.commons.menu.MenuItem;
import com.caved_in.commons.menu.menus.configmenu.WorldConfigMenu;
import org.bukkit.entity.Player;

public class OpenWorldConfigItem extends MenuItem {
	public OpenWorldConfigItem() {
		super("&dWorld Configuration", Items.getMaterialData(2, 0));
		setDescriptions("&eClick to open the world configuration menu");
	}

	@Override
	public void onClick(Player player) {
		getMenu().switchMenu(player, WorldConfigMenu.getInstance());
	}
}
