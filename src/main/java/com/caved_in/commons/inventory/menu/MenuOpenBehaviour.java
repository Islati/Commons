package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.inventory.menu.Menu;
import org.bukkit.entity.Player;

public interface MenuOpenBehaviour extends MenuBehaviour {
    @Override
    public void doAction(Menu menu, Player player);
}
