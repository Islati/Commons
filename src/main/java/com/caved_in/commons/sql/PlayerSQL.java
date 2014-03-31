package com.caved_in.commons.sql;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.player.PlayerWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PlayerSQL extends SQL {
	private Set<String> playersWithData = new HashSet<String>();
	private static String tableName = "players";
	private static String uuidFielf = "UUID";
	private static String playerField = "NAME";
	private static String onlineStatusField = "ONLINE";
	private static String serverField = "SERVER_NAME";
	private static String currencyField = "CURRENCY";
	private static String lastSeenField = "LAST_ONLINE";
	private static String premiumField = "PREMIUM";
	private static String prefixField = "PREFIX";

	// SELECT  `Prefix` FROM  `players` WHERE  `Name` =  'Squad_MC'

	private static String getPlayerPrefix = "SELECT Prefix FROM players WHERE name =?";

	private static String getPlayerDataStatement = "SELECT * FROM players WHERE name =?";
	private static String updatePlayerDataStatement = "UPDATE players SET playerField=?, LAST_ONLINE =?, " + currencyField + "=?, " +
			"" + serverField + "=?, " + premiumField + "=?, " + onlineStatusField + "=? WHERE " + playerField + "=?";
	private static String insertDefaultsStatement = "INSERT INTO " + tableName + " (" + playerField + ", " + onlineStatusField + ", " + serverField + ", " +
			"" + premiumField + ", " + currencyField + ", " + lastSeenField + ", " + prefixField + ") VALUES (?, 1, ?, 0, 0, ?, ?)";
	private static String updatePlayerCurrencyStatement = "UPDATE " + tableName + " SET " + currencyField + "=? WHERE " + playerField + "=?";
	private static String updatePlayerPremiumStatement = "UPDATE " + tableName + " SET " + premiumField + "=? WHERE " + playerField + "=?";

	private String creationStatement = "CREATE TABLE IF NOT EXISTS `[DB]`.`players` (" +
			"  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT," +
			"  `Name` text NOT NULL," +
			"  `OnlineStatus` tinyint(1) NOT NULL DEFAULT '0'," +
			"  `Server` text NOT NULL," +
			"  `XP` double unsigned NOT NULL," +
			"  `LastOnline` bigint(20) unsigned NOT NULL," +
			"  `Premium` tinyint(1) NOT NULL DEFAULT '0'," +
			"  `Prefix` text NOT NULL," +
			"  PRIMARY KEY (`ID`)" +
			") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";

	public PlayerSQL(SqlConfiguration sqlConfig) {
		super(sqlConfig.getHost(), sqlConfig.getPort(), sqlConfig.getDatabase(), sqlConfig.getUsername(), sqlConfig.getPassword());
		this.creationStatement = creationStatement.replace("[DB]", sqlConfig.getDatabase());
		execute(creationStatement);
	}


	public boolean hasData(String playerName) {
		if (!playersWithData.contains(playerName)) {
			PreparedStatement playerDataStatement = prepareStatement(getPlayerDataStatement);
			boolean hasData = false;
			try {
				playerDataStatement.setString(1, playerName);
				ResultSet playerData = playerDataStatement.executeQuery();
				hasData = playerData.next();
				//If the player has data, add it to the cache
				if (hasData) {
					playersWithData.add(playerName);
				}
				playerData.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(playerDataStatement);
			}
			return hasData;
		} else {
			return true;
		}
	}

	public PlayerWrapper getPlayerWrapper(String playerName) {
		//Create our SQL Statement
		PreparedStatement playerDataStatement = prepareStatement(getPlayerDataStatement);
		//Create a null player wrapper
		PlayerWrapper playerWrapper = null;
		//Check if the player doesn't have any data, and if they don't, create some!
		if (!hasData(playerName)) {
			insertDefaults(playerName);
		}
		try {
			playerDataStatement.setString(1, playerName);
			ResultSet playerData = playerDataStatement.executeQuery();
			if (playerData.next()) {
				//Build their player wrapper
				playerWrapper = new PlayerWrapper(playerName, playerData.getDouble(currencyField));
				playerWrapper.setPremium(playerData.getBoolean(premiumField));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(playerDataStatement);
		}
		return playerWrapper;
	}

	public boolean syncPlayerWrapperData(PlayerWrapper playerWrapper) {
		String playerName = playerWrapper.getName();
		PreparedStatement syncDataStatement = prepareStatement(updatePlayerDataStatement);
		boolean isSynced = false;
		if (hasData(playerName)) {
			try {
				//Set the players name variable
				syncDataStatement.setString(1, playerName);
				//Set the last time the player was online to NOW
				syncDataStatement.setLong(2, System.currentTimeMillis());
				//Update the players currency
				syncDataStatement.setDouble(3, playerWrapper.getCurrency());
				//Update the server which the player was on
				syncDataStatement.setString(4, playerWrapper.getServer());
				//Update the players premium status
				syncDataStatement.setBoolean(5, playerWrapper.isPremium());
				//Update the players online status
				syncDataStatement.setBoolean(6, playerWrapper.isOnline());
				//Set the search field to the players name
				syncDataStatement.setString(7, playerName);
				//Execute the update
				syncDataStatement.executeUpdate();
				isSynced = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				close(syncDataStatement);
			}
		} else {
			isSynced = insertDefaults(playerName);
		}
		return isSynced;
	}

	public boolean insertDefaults(String playerName) {
		PreparedStatement preparedStatement = prepareStatement(insertDefaultsStatement);
		boolean dataInserted = false;
		if (!hasData(playerName)) {
			try {
				//Set the players name
				preparedStatement.setString(1, playerName);
				//Set the servers name
				preparedStatement.setString(2, Commons.getConfiguration().getServerName());
				//Set the current time
				preparedStatement.setLong(3, System.currentTimeMillis());
				//Set the players prefix
				preparedStatement.setString(4, "[Member]");
				dataInserted = preparedStatement.execute();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
		return dataInserted;
	}

	public boolean updatePlayerCurrency(PlayerWrapper playerWrapper) {
		PreparedStatement preparedStatement = prepareStatement(updatePlayerCurrencyStatement);
		String playerName = playerWrapper.getName();
		boolean updatedCurrency = false;
		if (hasData(playerName)) {
			try {
				preparedStatement.setDouble(1, playerWrapper.getCurrency());
				preparedStatement.setString(2, playerName);
				preparedStatement.executeUpdate();
				updatedCurrency = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
		return updatedCurrency;
	}

	public boolean updatePlayerPremium(PlayerWrapper playerWrapper) {
		return updatePlayerPremium(playerWrapper.getName(), playerWrapper.isPremium());
	}

	public boolean updatePlayerPremium(String playerName, boolean isPremium) {
		PreparedStatement preparedStatement = prepareStatement(updatePlayerPremiumStatement);
		boolean wasUpdated = false;
		if (hasData(playerName)) {
			try {
				preparedStatement.setBoolean(1, isPremium);
				preparedStatement.setString(2, playerName);
				preparedStatement.executeUpdate();
				wasUpdated = true;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
		return wasUpdated;
	}


	public String getPrefix(PlayerWrapper playerWrapper) {
		PreparedStatement preparedStatement = prepareStatement(getPlayerPrefix);

		String playerName = playerWrapper.getName();

		String tag = "";

		if (hasData(playerName)) {
			try {
				preparedStatement.setString(1, playerName);
				ResultSet rs = preparedStatement.executeQuery();

				if (rs.next()) {
					tag = rs.getString(1);
				}

				//	tag = playerData.getString("Prefix");

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
		return tag;
	}
}
