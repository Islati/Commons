package com.caved_in.commons.sql;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.time.TimeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BansSQL extends SQL {
	private static String tableName = "bans";
	private static String idColumn = "ID";
	private static String typeColumn = "Type";
	private static String nameColumn = "Name";
	private static String getDataStatement = "SELECT * FROM " + tableName + " WHERE " + nameColumn + "=?";
	private static String reasonColumn = "Reason";
	private static String issuedByColumn = "IssuedBy";
	private static String timeIssuedColumn = "Issued";
	private static String expiryColumn = "Expires";
	private static String insertDataStatement = "INSERT INTO " + tableName + " (" + typeColumn + ", " + nameColumn + ", " + reasonColumn + ", " + issuedByColumn + ", " + timeIssuedColumn + ", " + expiryColumn + ") VALUES (?,?,?,?,?,?)";
	private static String activeColumn = "Active";
	private static String getActiveStatement = "SELECT * FROM " + tableName + " WHERE " + nameColumn + "=? AND " + activeColumn + "=?";
	private static String getActiveTypeStatement = "SELECT * FROM " + tableName + " WHERE " + nameColumn + "=? AND " + typeColumn + "=? AND " + activeColumn + "=?";
	private static String updateIDStatement = "UPDATE " + tableName + " SET " + activeColumn + "=? WHERE " + idColumn + "=?";
	private static String updateActiveStatement = "UPDATE " + tableName + " SET " + activeColumn + "=? WHERE " + nameColumn + "=?";
	private Map<String, Boolean> playersWithData = new HashMap<>();
	private String creationStatement = "CREATE TABLE IF NOT EXISTS `[DB]`.`bans` (" +
			"  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT," +
			"  `Type` text NOT NULL," +
			"  `Name` text NOT NULL," +
			"  `Reason` text NOT NULL," +
			"  `IssuedBy` text NOT NULL," +
			"  `Issued` bigint(20) unsigned NOT NULL," +
			"  `Expires` bigint(20) NOT NULL," +
			"  `Active` tinyint(1) NOT NULL DEFAULT '1'," +
			"  PRIMARY KEY (`ID`)" +
			") ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;";

	public BansSQL(SqlConfiguration sqlConfig) {
		super(sqlConfig.getHost(), sqlConfig.getPort(), sqlConfig.getDatabase(), sqlConfig.getUsername(), sqlConfig.getPassword());
		this.creationStatement = creationStatement.replace("[DB]", sqlConfig.getDatabase());
		execute(this.creationStatement);
	}

	private boolean hasData(String playerName) {
		boolean hasData = playersWithData.containsKey(playerName);
		if (!hasData) {
			//Prepare our statement
			PreparedStatement preparedStatement = prepareStatement(getDataStatement);
			try {
				//Set the search variable to the players name
				preparedStatement.setString(1, playerName);
				hasData = preparedStatement.executeQuery().next();
				if (hasData) {
					playersWithData.put(playerName, false);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
		return hasData;

	}

	public List<Punishment> getRecords(String playerName) {
		List<Punishment> punishments = new ArrayList<>();
		PreparedStatement preparedStatement = prepareStatement(getDataStatement);
		try {
			preparedStatement.setString(1, playerName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				punishments.add(new Punishment(resultSet.getString(typeColumn), resultSet.getLong(expiryColumn), resultSet.getLong(timeIssuedColumn), resultSet.getBoolean(activeColumn), resultSet.getString(reasonColumn), resultSet.getString(issuedByColumn)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return punishments;
	}

	public Punishment getLatestRecord(PunishmentType punishmentType, String playerName) {
		List<Punishment> punishmentRecords = this.getActiveRecords(punishmentType, playerName);
		if (!punishmentRecords.isEmpty()) {
			return punishmentRecords.get(punishmentRecords.size() - 1);
		}
		return null;
	}

	public boolean hasActiveData(String playerName) {
		if (hasData(playerName)) {
			if (!playersWithData.get(playerName)) {
				for (Punishment punishment : this.getRecords(playerName)) {
					if (punishment.isActive()) {
						//Cache the active data state
						playersWithData.put(playerName, true);
						return true;
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean hasActivePunishment(PunishmentType punishmentType, String playerName) {
		for (Punishment Record : this.getActiveRecords(playerName)) {
			if (Record.getPunishmentType() == punishmentType) {
				return true;
			}
		}
		return false;
	}

	public List<Punishment> getActiveRecords(PunishmentType type, String playerName) {
		List<Punishment> activeRecords = new ArrayList<>();
		for (Punishment record : this.getActiveRecords(playerName)) {
			if (record.getPunishmentType() == type) {
				activeRecords.add(record);
			}
		}
		return activeRecords;
	}

	public Set<Punishment> getActiveRecords(String playerName) {
		Set<Punishment> activeRecords = new HashSet<>();
		for (Punishment punishment : getRecords(playerName)) {
			if (punishment.isActive()) {
				activeRecords.add(punishment);
			}
		}
		return activeRecords;
	}

	public boolean isBanned(String playerName) {
		return (hasActivePunishment(PunishmentType.BAN, playerName));
	}

	public void insertPunishment(PunishmentType punishmentType, String playerName, String reason, String issuedBy, long expires) {
		PreparedStatement preparedStatement = prepareStatement(insertDataStatement);
		try {
			//Set the variables for our ban statement
			//Punishment type
			preparedStatement.setString(1, punishmentType.toString());
			//Player name
			preparedStatement.setString(2, playerName);
			//Reason for punishment
			preparedStatement.setString(3, reason);
			//Who the punishment might be issued by
			preparedStatement.setString(4, issuedBy);
			//When the punishment was issued
			preparedStatement.setLong(5, System.currentTimeMillis());
			//When the punishment expires
			preparedStatement.setLong(6, expires);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		//Player has active data
		playersWithData.put(playerName, true);
		Commons.messageConsole(punishmentType.toString() + " issued by [" + issuedBy + "] for player [" + playerName + "] for \"" + reason + " \" which will expire in " + TimeHandler.getDurationTrimmed(expires));
	}

	public boolean pardonPlayer(String playerName, String pardonedBy) {
		boolean pardoned = false;
		if (hasActiveData(playerName)) {
			PreparedStatement preparedStatement = prepareStatement(updateActiveStatement);
			try {
				preparedStatement.setBoolean(1, false);
				preparedStatement.setString(2, playerName);
				preparedStatement.executeUpdate();
				pardoned = true;
				Commons.messageConsole(playerName + " has been pardoned by " + pardonedBy);
				//Set the active cached data to false
				playersWithData.put(playerName, false);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}

		return pardoned;
	}

	public void pardonExpiredPunishments(String playerName) {
		//Our prepared statement for getting active punishment
		PreparedStatement activePunishmentsStatement = prepareStatement(getActiveStatement);
		//Our prepared statement for pardoning players
		PreparedStatement pardonStatement = prepareStatement(updateIDStatement);
		try {
			//Set the variables for our search statement
			activePunishmentsStatement.setString(1, playerName);
			activePunishmentsStatement.setBoolean(2, true);
			//Get the results from our search
			ResultSet activePunishments = activePunishmentsStatement.executeQuery();
			//Get the time we're checking against
			long currentTime = System.currentTimeMillis();
			//Loop through all active punishments
			while (activePunishments.next()) {
				//Check if the punishment has expired or not
				if (currentTime >= activePunishments.getLong(expiryColumn)) {
					//Change the "active" status of this punishment to false
					pardonStatement.setBoolean(1, false);
					//Set the ID to the active punishments ID
					pardonStatement.setInt(2, activePunishments.getInt(idColumn));
					pardonStatement.addBatch();
				}
			}
			//Execute our pool of batch statements
			pardonStatement.executeBatch();
			playersWithData.put(playerName, false);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//Close the search statement
			close(activePunishmentsStatement);
			//Close the batch pardon statement
			close(pardonStatement);
		}
	}
}
