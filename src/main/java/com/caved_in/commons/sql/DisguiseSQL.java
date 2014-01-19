package com.caved_in.commons.sql;

import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.disguises.Disguise;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisguiseSQL extends SQL{

	private static String dataTable = "disguises";
	private static String idTag = "ID";
	private static String nameTag = "Name";
	private static String disguisedTag = "DisguisedAs";
	private static String serverTag = "Server";

	private String creationStatement = "CREATE TABLE IF NOT EXISTS `[DB]`.`disguises` (" +
			"  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT," +
			"  `Name` text NOT NULL," +
			"  `DisguisedAs` text NOT NULL," +
			"  `Server` text NOT NULL," +
			"  PRIMARY KEY (`ID`)" +
			") ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;";

	private static String getDataStatement = "SELECT * FROM " + dataTable + " WHERE " + nameTag + "=?";
	private static String getDisguisesStatement = "SELECT * FROM " + dataTable;
	private static String insertDataStatement = "INSERT INTO " + dataTable + " (" + nameTag + ", " + disguisedTag + ", " + serverTag + ") VALUES (?,?,?)";
	private static String deleteDataStatement = "DELETE FROM " + dataTable + " WHERE " + nameTag + " =?";

	public DisguiseSQL(SqlConfiguration sqlConfig) {
		super(sqlConfig.getHost(), sqlConfig.getPort(), sqlConfig.getDatabase(), sqlConfig.getUsername(), sqlConfig.getPassword());
		this.creationStatement = creationStatement.replace("[DB]",sqlConfig.getDatabase());
		execute(creationStatement);
	}

	public boolean hasData(String playerName) {
		PreparedStatement preparedStatement = prepareStatement(getDataStatement);
		boolean hasData = false;
		try {
			preparedStatement.setString(1, playerName);
			hasData = preparedStatement.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return hasData;
	}

	public List<Disguise> getDisguises() {
		List<Disguise> activeDisguises = new ArrayList<Disguise>();
		PreparedStatement preparedStatement = prepareStatement(getDisguisesStatement);
		try {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				activeDisguises.add(new Disguise(resultSet.getString(nameTag), resultSet.getString(disguisedTag), resultSet.getString(serverTag)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return activeDisguises;
	}

	public Disguise getDisguise(String playerName) {
		Disguise playerDisguise = null;
		PreparedStatement preparedStatement = prepareStatement(getDataStatement);
		try {
			preparedStatement.setString(1, playerName);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				playerDisguise = new Disguise(playerName, resultSet.getString(disguisedTag), resultSet.getString(serverTag));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return playerDisguise;
	}

	public boolean addPlayerDisguiseData(Disguise disguise) {
		String playerName = disguise.getPlayerDisguised();
		boolean addedData = false;
		//Check if the player already has data, if so delete it
		if (hasData(playerName)) {
			deletePlayerDisguiseData(playerName);
		}
		//Prepare our data statement
		PreparedStatement preparedStatement = prepareStatement(insertDataStatement);
		try {
			//Set the statement variables
			preparedStatement.setString(1, playerName);
			preparedStatement.setString(2, disguise.getDisguisedAs());
			preparedStatement.setString(3, disguise.getServerOn());
			preparedStatement.executeUpdate();
			addedData = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return addedData;
	}

	public boolean deletePlayerDisguiseData(String playerName) {
		boolean deletedData = false;
		if (hasData(playerName)) {
			PreparedStatement preparedStatement = prepareStatement(deleteDataStatement);
			try {
				preparedStatement.setString(1, playerName);
				preparedStatement.executeUpdate();
				deletedData = true;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
		return deletedData;
	}
}
