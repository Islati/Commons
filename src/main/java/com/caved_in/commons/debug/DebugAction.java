package com.caved_in.commons.debug;

import org.bukkit.entity.Player;

public interface DebugAction {
	/**
	 * Perform a debug action using the player as its performer
	 *
	 * @param player
	 */
	public void doAction(Player player, String... args);

	public String getActionName();
}
