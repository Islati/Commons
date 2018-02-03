package com.devsteady.onyx.inventory.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface MenuItemClickHandler {
    void onClick(MenuItem item, Player player, ClickType type);
}
