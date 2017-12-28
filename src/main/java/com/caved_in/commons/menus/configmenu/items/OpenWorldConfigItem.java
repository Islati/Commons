package com.caved_in.commons.menus.configmenu.items;

import com.caved_in.commons.item.Items;
import com.caved_in.commons.inventory.menu.MenuItem;
import com.caved_in.commons.menus.configmenu.WorldConfigMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class OpenWorldConfigItem extends MenuItem {
    public OpenWorldConfigItem() {
        super("&dWorld Configuration", Items.getMaterialData(2, 0));
        setDescriptions("&eClick to open the world configuration menus");
    }

    @Override
	public void onClick(Player player, ClickType type) {
		getMenu().switchMenu(player, WorldConfigMenu.getInstance());
    }
}
