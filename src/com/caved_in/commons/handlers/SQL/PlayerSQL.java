package com.caved_in.commons.handlers.SQL;

import java.sql.ResultSet;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.handlers.Player.PlayerWrapper;

public class PlayerSQL
{
	private SQL playerSql;
	private String tableName = "Players";
	private String playerField = "Name";
	private String onlineStatusField = "OnlineStatus";
	private String serverField = "Server";
	private String currencyField = "XP";
	private String lastSeenField = "LastOnline";
	private String premiumField = "Premium";

	public PlayerSQL(SqlConfiguration sqlConfig)
	{
		this.playerSql = new SQL(sqlConfig.getHost(), sqlConfig.getPort(), sqlConfig.getDatabase(), sqlConfig.getUsername(), sqlConfig.getPassword());
	}

	private ResultSet getData(String playerName)
	{
		return this.playerSql.executeQueryOpen("SELECT * FROM " + this.tableName + " WHERE " + this.playerField + "='" + playerName + "'");
	}

	public boolean hasData(String playerName)
	{
		ResultSet playerData = this.getData(playerName);
		try
		{
			boolean hasData = playerData.next();
			playerData.close();
			return hasData;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public PlayerWrapper getPlayerWrapper(String playerName)
	{
		if (hasData(playerName))
		{
			try
			{
				ResultSet playerData = this.getData(playerName);
				if (playerData.next())
				{
					PlayerWrapper playerWrapper = new PlayerWrapper(playerName, playerData.getDouble(this.currencyField)); //Initiate a new player instance
					playerWrapper.setPremium(playerData.getBoolean(this.premiumField)); //Set the users premium status based on what it says in SQL
					playerData.close(); //Close the sql
					return playerWrapper; //Return the player wrapper
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		else
		{
			try
			{
				insertDefaults(playerName);
				ResultSet playerData = this.getData(playerName);
				if (playerData.next())
				{
					PlayerWrapper playerWrapper = new PlayerWrapper(playerName, playerData.getDouble(this.currencyField));//Initiate a new player instance
					playerWrapper.setPremium(playerData.getBoolean(this.premiumField));//Set the users premium status based on what it says in SQL
					playerData.close(); //Close the sql
					return playerWrapper; //Return the player wrapper
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public boolean syncPlayerWrapperData(PlayerWrapper playerWrapper)
	{
		if (hasData(playerWrapper.getName()))
		{
			try
			{
				String sqlStatement = "UPDATE " + this.tableName + " SET " + this.playerField + "='" + playerWrapper.getName() + "'," + this.lastSeenField + "='" + System.currentTimeMillis() + "', " + this.currencyField + "='" + playerWrapper.getCurrency() + "', " + this.serverField + "='" + playerWrapper.getServer() + "', " + this.premiumField + "='" + (playerWrapper.isPremium() ? "1" : "0") + "', " + this.onlineStatusField + "='" + (playerWrapper.isOnline() ? "1" : "0") + "' WHERE " + this.playerField + "='" + playerWrapper.getName() + "';";
				this.playerSql.executeUpdate(sqlStatement);
				return true;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		else
		{
			return insertDefaults(playerWrapper.getName());
		}
	}

	public boolean insertDefaults(String playerName)
	{
		if (!hasData(playerName))
		{
			try
			{
				String sqlStatement = "INSERT INTO " + this.tableName + " (" + this.playerField + ", " + this.onlineStatusField + ", " + this.serverField + ", " + this.premiumField + ", " + this.currencyField + ", " + this.lastSeenField + ") VALUES ('" + playerName + "', '1', '" + Commons.getConfiguration().getServerName() + "', '0', '0', '" + System.currentTimeMillis() + "');";
				this.playerSql.executeUpdate(sqlStatement);
				return true;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean updatePlayerCurrency(PlayerWrapper playerWrapper)
	{
		if (hasData(playerWrapper.getName()))
		{
			String sqlStatement = "UPDATE " + this.tableName + " SET " + this.currencyField + "='" + playerWrapper.getCurrency() + "' WHERE " + this.playerField + "='" + playerWrapper.getName() + "';";
			try
			{
				this.playerSql.executeUpdate(sqlStatement);
				return true;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean updatePlayerPremium(PlayerWrapper playerWrapper)
	{
		if (hasData(playerWrapper.getName()))
		{
			String sqlStatement = "UPDATE " + this.tableName + " SET " + this.premiumField + "='" + (playerWrapper.isPremium() ? "1" : "0") + "' WHERE " + this.playerField + "='" + playerWrapper.getName() + "';";
			try
			{
				this.playerSql.executeUpdate(sqlStatement);
				return true;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean updatePlayerPremium(String playerName, boolean isPremium)
	{
		if (hasData(playerName))
		{
			String sqlStatement = "UPDATE " + this.tableName + " SET " + this.premiumField + "='" + (isPremium ? "1" : "0") + "' WHERE " + this.playerField + "='" + playerName + "';";
			try
			{
				this.playerSql.executeUpdate(sqlStatement);
				return true;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public void refreshConnection()
	{
		this.playerSql.refreshConnection();
	}
}
