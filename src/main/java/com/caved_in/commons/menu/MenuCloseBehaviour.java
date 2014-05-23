package com.caved_in.commons.menu;

import org.bukkit.entity.Player;

public interface MenuCloseBehaviour extends MenuBehaviour {

	/**
	 * Called when the menu's closed
	 *
	 * @param player the player closing the menu
	 */
	public void doAction(Player player);
}
