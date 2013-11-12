package com.caved_in.commons.handlers.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.handlers.Friends.Friend;

import org.bukkit.Bukkit;

public class FriendSQL
{
	private SQL SQL;

	private String tableName = "Friends";
	private String playerField = "Player";
	private String friendField = "Friend";
	private String acceptedField = "Accepted";

	/**
	 * 
	 * @param sqlConfig
	 */
	public FriendSQL(SqlConfiguration sqlConfig)
	{
		this.SQL = new SQL(sqlConfig.getHost(), sqlConfig.getPort(), sqlConfig.getDatabase(), sqlConfig.getUsername(), sqlConfig.getPassword());
	}

	/**
	 * 
	 */
	public void refresh()
	{
		this.SQL.refreshConnection();
	}

	/**
	 * Echo a message to bukkit console
	 * 
	 * @param Message
	 *            Message to Echo
	 */
	public void consoleOutput(String Message)
	{
		Bukkit.getLogger().info(Message);
	}

	/**
	 * Gets the data for a player via ResultSet
	 * 
	 * @param playerName
	 *            Name to get data of
	 * @return ResultSet of Data
	 */
	public ResultSet getPlayerData(String playerName)
	{
		return this.SQL.executeQueryOpen("SELECT * FROM " + this.tableName + " WHERE " + this.playerField + "= '" + playerName + "';");
	}

	public boolean hasData(String playerName)
	{
		ResultSet playerData = this.getPlayerData(playerName);
		try
		{
			boolean returnValue = false;
			returnValue = playerData.next();
			playerData.close();
			return returnValue;
		}
		catch (Exception Ex)
		{
			Ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param playerName
	 * @return
	 */
	public List<Friend> getFriends(String playerName)
	{
		List<Friend> playerFriends = new ArrayList<Friend>();
		if (this.hasData(playerName))
		{
			ResultSet playerData = this.getPlayerData(playerName);
			try
			{
				while (playerData.next())
				{
					String friendName = playerData.getString(friendField);
					playerFriends.add(new Friend(playerName, friendName, playerData.getBoolean(this.acceptedField)));
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return playerFriends;
	}

	public List<Friend> getUnacceptedFriends(String playerName)
	{
		List<Friend> unacceptedFriends = new ArrayList<Friend>();
		if (this.hasData(playerName))
		{
			ResultSet playerData = this.getPlayerData(playerName);
			try
			{
				while (playerData.next())
				{
					if (playerData.getBoolean(this.acceptedField) == false)
					{
						unacceptedFriends.add(new Friend(playerName, playerData.getString(this.friendField), false));
					}
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return unacceptedFriends;
	}

	/**
	 * 
	 * @param Player
	 * @param Friend
	 * @return
	 */
	public boolean isPlayerFriendsWith(String Player, String Friend)
	{
		if (this.hasData(Player))
		{
			ResultSet playerResults = this.SQL.executeQueryOpen("SELECT * FROM " + this.tableName + " WHERE " + this.playerField + "='" + Player + "' AND " + this.friendField + "='" + Friend + "';");
			try
			{
				boolean isAccepted = false;
				if (playerResults.next())
				{
					isAccepted = playerResults.getBoolean(this.acceptedField);
				}
				playerResults.close();
				return isAccepted;
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 
	 * @param Player
	 * @param From
	 * @return
	 */
	public boolean hasFriendRequest(String Player, String From)
	{
		if (this.hasData(Player))
		{
			ResultSet playerResults = this.SQL.executeQueryOpen("SELECT * FROM " + this.tableName + " WHERE " + this.playerField + "='" + Player + "' AND " + this.friendField + "='" + From + "' AND " + this.acceptedField + "='0';");
			try
			{
				boolean hasRequest = false;
				if (playerResults.next())
				{
					hasRequest = true;
				}
				playerResults.close();
				return hasRequest;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 
	 * @param Player
	 * @param Friend
	 * @return
	 */
	public FriendStatus acceptFriendRequest(String Player, String Friend)
	{
		if (hasFriendRequest(Player, Friend) && hasFriendRequest(Friend, Player))
		{
			try
			{
				String playerAcceptFriendQuery = "UPDATE " + this.tableName + " SET " + this.acceptedField + "='1' WHERE " + this.playerField + "='" + Player + "' AND " + this.friendField + "='" + Friend + "';";
				String friendAcceptPlayerQuery = "UPDATE " + this.tableName + " SET " + this.acceptedField + "='1' WHERE " + this.playerField + "='" + Friend + "' AND " + this.friendField + "='" + Player + "';";
				this.SQL.executeUpdate(playerAcceptFriendQuery);
				this.SQL.executeUpdate(friendAcceptPlayerQuery);
				return FriendStatus.ACCEPTED;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return FriendStatus.ERROR;
			}
		}
		else
		{
			return FriendStatus.NO_REQUEST;
		}
	}

	/**
	 * 
	 * @param playerSending
	 * @param friendRequested
	 */
	public FriendStatus insertFriendRequest(String playerSending, String friendRequested)
	{
		if (!this.hasFriendRequest(playerSending, friendRequested) && !this.hasFriendRequest(friendRequested, playerSending))
		{
			if (!this.isPlayerFriendsWith(playerSending, friendRequested) && !this.isPlayerFriendsWith(friendRequested, playerSending))
			{
				String playerRequestFriend = "INSERT INTO " + this.tableName + " (" + this.playerField + ", " + this.friendField + ", " + this.acceptedField + ") VALUES ('" + playerSending + "', '" + friendRequested + "', '1');";
				String friendRequestPlayer = "INSERT INTO " + this.tableName + " (" + this.playerField + ", " + this.friendField + ", " + this.acceptedField + ") VALUES ('" + friendRequested + "', '" + playerSending + "', '0');";
				try
				{
					this.SQL.executeUpdate(playerRequestFriend);
					this.SQL.executeUpdate(friendRequestPlayer);
					return FriendStatus.REQUESTED;
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					return FriendStatus.ERROR;
				}
			}
			else
			{
				return FriendStatus.ALREADY_FRIENDS;
			}
		}
		else
		{
			return FriendStatus.ALREADY_PENDING;
		}
	}

	/**
	 * 
	 * @param playerToDeleteFrom
	 * @param deletingRequestOf
	 */
	public void deleteFriendRequest(String playerToDeleteFrom, String deletingRequestOf)
	{
		String deleteSqlStatementPlayer = "DELETE FROM " + this.tableName + " WHERE " + this.playerField + "='" + playerToDeleteFrom + "' AND " + this.friendField + "='" + deletingRequestOf + "';";
		String deleteSqlStatementFriend = "DELETE FROM " + this.tableName + " WHERE " + this.playerField + "='" + deletingRequestOf + "' AND " + this.friendField + "='" + playerToDeleteFrom + "';";
		try
		{
			this.SQL.executeUpdate(deleteSqlStatementPlayer);
			this.SQL.executeUpdate(deleteSqlStatementFriend);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
