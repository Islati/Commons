package com.caved_in.commons.debug;

import org.bukkit.entity.Player;

public interface DebugAction {
	/**
	 * Perform a debug action using the player as its performer
	 *
	 * @param player who's performing the debug action
	 * @param args   extra arguments to be passed to the action.
	 */
	public void doAction(Player player, String... args);

	/**
	 * @return the actions name.
	 */
	public String getActionName();
}
