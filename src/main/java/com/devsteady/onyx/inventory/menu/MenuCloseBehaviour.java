package com.devsteady.onyx.inventory.menu;

import org.bukkit.entity.Player;

public interface MenuCloseBehaviour extends MenuBehaviour {

    /**
     * Called when the menus's closed
     *
     * @param menu   the menus that's being closed
     * @param player the player closing the menus
     */
    void doAction(Menu menu, Player player);
}
