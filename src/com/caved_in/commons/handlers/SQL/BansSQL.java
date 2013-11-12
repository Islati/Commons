package com.caved_in.commons.handlers.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.handlers.Misc.PunishmentUtils;
import com.caved_in.commons.handlers.Data.Bans.Punishment;
import com.caved_in.commons.handlers.Data.Bans.PunishmentType;
import com.caved_in.commons.handlers.Misc.TimeHandler;

import org.bukkit.Bukkit;

public class BansSQL
{
	private SQL SQL;

	private String Table = "Bans";
	private String IDTag = "ID";
	private String TypeTag = "Type";
	private String NameTag = "Name";
	private String ReasonTag = "Reason";
	private String IssuedByTag = "IssuedBy";
	private String IssuedTag = "Issued";
	private String ExpiresTag = "Expires";
	private String ActiveTag = "Active";

	public BansSQL(SqlConfiguration sqlConfig)
	{
		this.SQL = new SQL(sqlConfig.getHost(), sqlConfig.getPort(), sqlConfig.getDatabase(), sqlConfig.getUsername(), sqlConfig.getPassword());
	}

	public void Refresh()
	{
		this.SQL.refreshConnection();
	}

	/**
	 * Echo a message to bukkit console
	 * 
	 * @param Message
	 *            Message to Echo
	 */
	public void Console(String Message)
	{
		Bukkit.getLogger().info(Message);
	}

	/**
	 * Gets the data for a player via ResultSet
	 * 
	 * @param PlayerName
	 *            Name to get data of
	 * @return ResultSet of Data
	 */
	public ResultSet getPlayerData(String PlayerName)
	{
		return this.SQL.executeQueryOpen("SELECT * FROM " + this.Table + " WHERE " + this.NameTag + " = '" + PlayerName + "';");
	}

	/**
	 * Does the player have data?
	 * 
	 * @param PlayerName
	 *            Name to check
	 * @return true if they do, false otherwise
	 */
	private boolean hasData(String PlayerName)
	{
		try
		{
			ResultSet OpenStatement = this.SQL.executeQueryOpen("SELECT * FROM " + this.Table + " WHERE " + this.NameTag + " = '" + PlayerName + "';");
			boolean HasNext = OpenStatement.next();
			OpenStatement.close();
			return HasNext;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ALL punishment records for the given player
	 * 
	 * @param PlayerName
	 * @return List of all Punishments, active and expired
	 */
	public List<Punishment> getRecords(String PlayerName)
	{
		List<Punishment> Punishments = new ArrayList<Punishment>();
		try
		{
			ResultSet OpenStatement = this.SQL.executeQueryOpen("SELECT * FROM " + this.Table + " WHERE " + this.NameTag + " = '" + PlayerName + "';");
			while (OpenStatement.next())
			{
				Punishment Record = PunishmentUtils.getPunishment(OpenStatement.getString(this.TypeTag), OpenStatement.getLong(this.ExpiresTag), OpenStatement.getLong(this.IssuedTag), OpenStatement.getBoolean(this.ActiveTag), OpenStatement.getString(this.ReasonTag), OpenStatement.getString(this.IssuedByTag));
				if (Record != null)
				{
					Punishments.add(Record);
				}
			}
			OpenStatement.close();
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
		}
		return Punishments;
	}

	public Punishment getLatestRecord(PunishmentType Type, String PlayerName)
	{
		List<Punishment> Records = this.getActiveRecords(Type, PlayerName);
		if (!Records.isEmpty())
		{
			return Records.get(Records.size() - 1);
		}
		return null;
	}

	/**
	 * Checks if the player has any active punishments (Bans, Mutes,
	 * infractions, etc)
	 * 
	 * @param PlayerName
	 * @return true if they do, false otherwise
	 */
	public boolean hasActiveData(String PlayerName)
	{
		for (Punishment Record : this.getRecords(PlayerName))
		{
			if (Record.isActive())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the player has any active punishments of the specified type
	 * 
	 * @param Type
	 * @param PlayerName
	 * @return true if so, false otherwise
	 */
	public boolean hasActivePunishment(PunishmentType Type, String PlayerName)
	{
		for (Punishment Record : this.getActiveRecords(PlayerName))
		{
			if (Record.getType() == Type)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * List of all the Active punishments of the specified type for the player
	 * requested
	 * 
	 * @param Type
	 * @param PlayerName
	 * @return
	 */
	public List<Punishment> getActiveRecords(PunishmentType Type, String PlayerName)
	{
		List<Punishment> Records = new ArrayList<Punishment>();
		for (Punishment Record : this.getActiveRecords(PlayerName))
		{
			if (Record.getType() == Type)
			{
				Records.add(Record);
			}
		}
		return Records;
	}

	/**
	 * Gets all active records for the given player
	 * 
	 * @param PlayerName
	 * @return
	 */
	public List<Punishment> getActiveRecords(String PlayerName)
	{
		List<Punishment> ActiveRecords = new ArrayList<Punishment>();
		for (Punishment Record : this.getRecords(PlayerName))
		{
			if (Record.isActive())
			{
				ActiveRecords.add(Record);
			}
		}
		return ActiveRecords;
	}

	private PunishmentType getPunishmentType(ResultSet Set) throws SQLException
	{
		return PunishmentUtils.getType(Set.getString(this.TypeTag));
	}

	/**
	 * Is the player currently serving an active ban punishment?
	 * 
	 * @param PlayerName
	 * @return true if they are, false otherwise
	 */
	public boolean isBanned(String PlayerName)
	{
		return (this.hasActivePunishment(PunishmentType.Ban, PlayerName));
	}

	/**
	 * Issue a punishment
	 * 
	 * @param Type
	 * @param PlayerName
	 * @param Reason
	 * @param IssuedBy
	 * @param Expires
	 */
	public void InsertPunishment(PunishmentType Type, String PlayerName, String Reason, String IssuedBy, long Expires)
	{
		this.SQL.executeUpdate("INSERT INTO " + this.Table + " (Type, Name, Reason, IssuedBy, Issued, Expires) VALUES ('" + Type.name().toLowerCase() + "','" + PlayerName + "','" + Reason + "','" + IssuedBy + "','" + System.currentTimeMillis() + "','" + Expires + "');");
		Console(Type.name() + " issued by [" + IssuedBy + "] for player [" + PlayerName + "] for \"" + Reason + " \" which will expire in " + TimeHandler.getDurationTrimmed(Expires));
	}

	/**
	 * Pardon a player from all punishments / offences
	 * 
	 * @param PlayerName
	 * @param UnbannedBy
	 * @return
	 */
	public boolean Pardon(String PlayerName, String UnbannedBy)
	{
		if (this.hasActiveData(PlayerName))
		{
			this.SQL.executeUpdate("UPDATE " + this.Table + " SET " + this.ActiveTag + "='0' WHERE " + this.NameTag + "='" + PlayerName + "';");
			Console(PlayerName + " has been pardoned by " + UnbannedBy);
			return true;
		}
		return false;
	}

	/**
	 * Clear a player of all their active mutes
	 * 
	 * @param PlayerName
	 * @param UnmuteBy
	 * @return
	 */
	public boolean Unmute(String PlayerName, String UnmuteBy)
	{
		if (this.hasActivePunishment(PunishmentType.Mute, PlayerName))
		{
			try
			{
				this.SQL.executeUpdate("UPDATE " + this.Table + " SET " + this.ActiveTag + "='0' WHERE " + this.NameTag + "='" + PlayerName + "' AND " + this.TypeTag + "='mute';");
				Console(PlayerName + " has been unmuted by " + UnmuteBy);
				return true;
			}
			catch (Exception Ex)
			{
				Ex.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Pardon a player of all expired punishments of the specified type
	 * 
	 * @param Type
	 * @param Player
	 */
	public void PardonAllExpiredPunishments(PunishmentType Type, String Player)
	{
		List<String> Statements = new ArrayList<String>();
		ResultSet PlayerData = this.SQL.executeQueryOpen("SELECT * FROM " + this.Table + " WHERE " + this.NameTag + "='" + Player + "' AND " + this.TypeTag + "='" + Type.name().toLowerCase() + "' AND " + this.ActiveTag + "='1';");
		try
		{
			while (PlayerData.next())
			{
				long Expires = PlayerData.getLong(this.ExpiresTag);
				if (System.currentTimeMillis() >= Expires)
				{
					Statements.add("UPDATE " + this.Table + " SET " + this.ActiveTag + "='0' WHERE ID='" + PlayerData.getInt("ID") + "';");
				}
			}
			PlayerData.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		for (String Statement : Statements)
		{
			this.SQL.executeUpdate(Statement);
		}
	}

	/**
	 * Pardon a player of all expired punishments of the specified type
	 * 
	 * @param Type
	 * @param Player
	 */
	public void PardonAllExpiredPunishments(String Player)
	{
		List<String> Statements = new ArrayList<String>();
		ResultSet PlayerData = this.SQL.executeQueryOpen("SELECT * FROM " + this.Table + " WHERE " + this.NameTag + "='" + Player + "' AND " + this.ActiveTag + "='1';");
		try
		{
			while (PlayerData.next())
			{
				long Expires = PlayerData.getLong(this.ExpiresTag);
				if (System.currentTimeMillis() >= Expires)
				{
					Statements.add("UPDATE " + this.Table + " SET " + this.ActiveTag + "='0' WHERE ID='" + PlayerData.getInt("ID") + "';");
				}
			}
			PlayerData.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		for (String Statement : Statements)
		{
			this.SQL.executeUpdate(Statement);
		}
	}
}
