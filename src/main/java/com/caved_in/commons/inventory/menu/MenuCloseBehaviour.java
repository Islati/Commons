package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.inventory.menu.Menu;
import org.bukkit.entity.Player;

public interface MenuCloseBehaviour extends MenuBehaviour {

    /**
     * Called when the menus's closed
     *
     * @param menu   the menus that's being closed
     * @param player the player closing the menus
     */
    public void doAction(Menu menu, Player player);
}
