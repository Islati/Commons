package com.caved_in.commons.sql;

import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.data.disguises.Disguise;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisguiseSQL {
	private SQL SQL;

	private String dataTable = "Disguises";
	private String idTag = "ID";
	private String nameTag = "Name";
	private String disguisedTag = "DisguisedAs";
	private String serverTag = "Server";

	public DisguiseSQL(SqlConfiguration sqlConfig) {
		this.SQL = new SQL(sqlConfig.getHost(), sqlConfig.getPort(), sqlConfig.getDatabase(), sqlConfig.getUsername(), sqlConfig.getPassword());
	}

	public void refreshConnection() {
		this.SQL.refreshConnection();
	}

	/**
	 * Echo a message to bukkit console
	 *
	 * @param Message Message to Echo
	 */
	public void Console(String Message) {
		Bukkit.getLogger().info(Message);
	}

	/**
	 * Gets the data for a player via ResultSet
	 *
	 * @param PlayerName Name to get data of
	 * @return ResultSet of Data
	 */
	public ResultSet getPlayerData(String PlayerName) {
		return this.SQL.executeQueryOpen("SELECT * FROM " + this.dataTable + " WHERE " + this.nameTag + " = '" + PlayerName + "';");
	}

	/**
	 * Does the player have data?
	 *
	 * @param PlayerName Name to check
	 * @return true if they do, false otherwise
	 */
	public boolean hasData(String PlayerName) {
		try {
			ResultSet OpenStatement = this.SQL.executeQueryOpen("SELECT * FROM " + this.dataTable + " WHERE " + this.nameTag + " = '" + PlayerName + "';");
			boolean HasNext = OpenStatement.next();
			OpenStatement.close();
			return HasNext;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Disguise> getDisguises() {
		List<Disguise> activeDisguises = new ArrayList<Disguise>();
		try {
			ResultSet disguiseStatement = this.SQL.executeQueryOpen("SELECT * FROM " + this.dataTable + ";");
			while (disguiseStatement.next()) {
				Disguise playerDisguise = new Disguise(disguiseStatement.getString(this.nameTag), disguiseStatement.getString(this.disguisedTag), disguiseStatement.getString(this.serverTag));
				activeDisguises.add(playerDisguise);
			}
			disguiseStatement.close();
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return activeDisguises;
	}

	public Disguise getDisguise(String playerName) {
		Disguise playerDisguise = null;
		try {
			ResultSet disguiseStatement = this.SQL.executeQueryOpen("SELECT * FROM " + this.dataTable + " WHERE " + this.nameTag + " ='" + playerName + "';");
			if (disguiseStatement.next()) {
				playerDisguise = new Disguise(playerName, disguiseStatement.getString(this.disguisedTag), disguiseStatement.getString(this.serverTag));
			}
			disguiseStatement.close();
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return playerDisguise;
	}

	public boolean addPlayerDisguiseData(Disguise disguise) {
		try {
			if (this.hasData(disguise.getPlayerDisguised())) {
				this.deletePlayerDisguiseData(disguise.getPlayerDisguised());
			}
			this.SQL.executeUpdate("INSERT INTO " + this.dataTable + " (" + this.nameTag + ", " + this.disguisedTag + ", " + this.serverTag + ") VALUES ('" + disguise.getPlayerDisguised() + "','" + disguise.getDisguisedAs() + "','" + disguise.getServerOn() + "');");
			return true;
		} catch (Exception Ex) {
			Ex.printStackTrace();
			return false;
		}
	}

	public boolean deletePlayerDisguiseData(String playerName) {
		try {
			if (this.hasData(playerName)) {
				this.SQL.executeUpdate("DELETE FROM " + this.dataTable + " WHERE " + this.nameTag + " ='" + playerName + "';");
				return true;
			}
			return false;
		} catch (Exception Ex) {
			return false;
		}
	}
}
