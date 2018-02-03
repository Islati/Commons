package com.devsteady.onyx.inventory.menu;

import org.bukkit.entity.Player;

public interface MenuBehaviour {

    /**
     * Called when an action is performed in the menus.
     *
     * @param menu   the menus this behaviour is being executed from
     * @param player the player performing the action.
     */
    void doAction(Menu menu, Player player);
}
