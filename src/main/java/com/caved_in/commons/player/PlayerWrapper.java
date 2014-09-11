package com.caved_in.commons.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlayerWrapper {
	/**
	 * @return name of the player
	 */
	public String getName();

	/**
	 * @return the players uuid
	 */
	public UUID getId();

	/**
	 * @return player whose data is being wrapped.
	 */
	public Player getPlayer();
}
