package com.caved_in.commons.menu.menus.configmenu.items;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.item.Wool;
import com.caved_in.commons.menu.MenuItem;
import org.bukkit.entity.Player;

public class HasSqlBackendItem extends MenuItem {

	public HasSqlBackendItem() {
		super();
		init();
	}

	private void init() {
		boolean hasSqlBackend = Commons.hasSqlBackend();
		if (hasSqlBackend) {
			setText("&aMySql Backend");
			setIcon(Wool.GREEN_WOOL);
			setDescriptions("&eClick to disable the MySQL Backend");
		} else {
			setText("&cMySql Backend");
			setIcon(Wool.RED_WOOL);
			setDescriptions("&eClick to enable the MySQL Backend");
		}
	}

	@Override
	public void onClick(Player player) {
		Configuration config = Commons.getConfiguration();
		boolean hasSqlBackend = !config.hasSqlBackend();
		config.setSqlBackend(hasSqlBackend);
		init();
		getMenu().updateMenu();
	}
}
