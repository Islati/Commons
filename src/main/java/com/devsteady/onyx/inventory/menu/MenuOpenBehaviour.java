package com.devsteady.onyx.inventory.menu;

import org.bukkit.entity.Player;

public interface MenuOpenBehaviour extends MenuBehaviour {
    @Override
    void doAction(Menu menu, Player player);
}
