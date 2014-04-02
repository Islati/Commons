package com.caved_in.commons.sql;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.player.PlayerWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static com.caved_in.commons.sql.DatabaseField.*;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class OnlineStatusConnector extends SQL {

	private static final String UPDATE_PLAYER_ONLINE_STATUS = "REPLACE INTO online SET " + ONLINE_STATUS + "=? AND " + SERVER_ID + "=? WHERE " + PLAYER_UNIQUE_ID + "=?";

	public OnlineStatusConnector(SqlConfiguration sqlConfiguration) {
		super(sqlConfiguration);
	}

	public boolean updateOnlineStatus(UUID uniqueId, boolean isOnline) {
		boolean dataUpdated = false;
		PreparedStatement statement = prepareStatement(UPDATE_PLAYER_ONLINE_STATUS);
		try {
			//Assign the values to the prepared statement
			statement.setBoolean(1, isOnline);
			statement.setInt(2, Commons.getServerId());
			statement.setString(3,uniqueId.toString());
			statement.executeUpdate();
			dataUpdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement);
		}
		return dataUpdated;
	}

	public boolean updateOnlineStatus(PlayerWrapper playerWrapper, boolean isOnline) {
		return updateOnlineStatus(playerWrapper.getUniqueId(),isOnline);
	}
}
