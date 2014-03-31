package com.caved_in.commons.sql;

import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.player.PlayerWrapper;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class PlayerDataConnector extends SQL {
	private static final String PLAYER_TABLE = "PLAYERS";
	private static final String PLAYER_UNIQUE_ID = "PL_UID";
	private static final String PLAYER_NAME = "PL_NAME";

	private static final String GET_NAME_STATEMENT = "SELECT " + PLAYER_NAME + " FROM " + PLAYER_TABLE + " WHERE PL_UID=?";

	public PlayerDataConnector(SqlConfiguration sqlConfiguration) {
		super(sqlConfiguration);
	}

	public PlayerWrapper getPlayerWrapper(Player player) {
		return getPlayerWrapper(player.getUniqueId());
	}

	public PlayerWrapper getPlayerWrapper(UUID playerId) {
		return null;
	}

	public String getPlayerName(UUID uniqueId) {
		return null;
	}


}
