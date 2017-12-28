package com.caved_in.commons.menu.inventory;

import com.caved_in.commons.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface MenuItemClickHandler {
    void onClick(MenuItem item, Player player, ClickType type);
}
