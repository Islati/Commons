package com.caved_in.commons.sql;

import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.player.PlayerWrapper;
import org.bukkit.entity.Player;

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
public class PlayerDataConnector extends SQL {

	private static final String GET_PLAYER_DATA_STATEMENT = "SELECT " + PLAYER_TABLE + "." + PLAYER_CURRENCY + ", " +
			"" + PLAYER_PREFIX_TABLE + "." + PLAYER_NAME_PREFIX + ", " + PREMIUM_TABLE + "." + PREMIUM_STATUS
			+ " FROM " + PLAYER_PREFIX_TABLE + ", " + PREMIUM_TABLE + " WHERE " + PLAYER_PREFIX_TABLE + "." + PLAYER_UNIQUE_ID + "=? AND " + PREMIUM_TABLE + "" +
			"." + PREMIUM_STATUS + "=? AND " + PLAYER_TABLE + "." + PLAYER_UNIQUE_ID + "=?";

	public PlayerDataConnector(SqlConfiguration sqlConfiguration) {
		super(sqlConfiguration);
	}

	public PlayerWrapper getPlayerWrapper(Player player) {
		return getPlayerWrapper(player.getUniqueId());
	}

	public PlayerWrapper getPlayerWrapper(UUID playerId) {
		PlayerWrapper playerWrapper = null;
		PreparedStatement statement = prepareStatement(GET_PLAYER_DATA_STATEMENT);
		try {
			//Assign the first 3 variables in the statement to the unique ID (user id)
			String uidString = playerId.toString();
			for (int i = 0; i < 3; i++) {
				statement.setString(i, uidString);
			}
			//Execute the query and get the resultset from it
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				//Get the players currency, prefix, and premium status
				double playerCurrency = resultSet.getDouble(1);
				String playerPrefix = resultSet.getString(2);
				boolean playerPremium = resultSet.getBoolean(3);
				//Create a new player wrapper and assign the variables pulled from the database
				playerWrapper = new PlayerWrapper(playerId);
				playerWrapper.setCurrency(playerCurrency);
				playerWrapper.setPremium(playerPremium);
				playerWrapper.setPrefix(playerPrefix);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(statement);
		}
		return playerWrapper;
	}


}
