package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.inventory.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface MenuItemClickHandler {
    void onClick(MenuItem item, Player player, ClickType type);
}
