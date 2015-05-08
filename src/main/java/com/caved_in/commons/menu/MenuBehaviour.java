package com.caved_in.commons.menu;

import org.bukkit.entity.Player;

public interface MenuBehaviour {

    /**
     * Called when an action is performed in the menu.
     *
     * @param menu   the menu this behaviour is being executed from
     * @param player the player performing the action.
     */
    public void doAction(ItemMenu menu, Player player);
}
