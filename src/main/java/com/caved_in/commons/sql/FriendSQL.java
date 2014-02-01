package com.caved_in.commons.sql;

import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.friends.Friend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendSQL extends SQL {
	private static String tableName = "friends";
	private static String playerField = "player";
	private static String friendField = "Friend";
	private static String acceptedField = "Accepted";

	private String creationStatement = "CREATE TABLE IF NOT EXISTS `[DB]`.`friends` (" +
			"  `player` text NOT NULL," +
			"  `Friend` text NOT NULL," +
			"  `Accepted` tinyint(1) NOT NULL DEFAULT '0'" +
			") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

	private static String getPlayerDataStatement = "SELECT * FROM " + tableName + " WHERE " + playerField + "=?";
	private static String getFriendsStatusStatement = "SELECT * FROM " + tableName + " WHERE " + playerField + "=? AND " + friendField + "=?";
	private static String hasRequestStatement = "SELECT * FROM " + tableName + " WHERE " + playerField + "=? AND " + friendField + "=? AND " + acceptedField +
			"=?";
	private static String acceptFriendStatement = "UPDATE " + tableName + " SET " + acceptedField + "=? WHERE " + playerField + "=? AND " + friendField + "=?";
	private static String insertFriendRequest = "INSERT INTO " + tableName + " (" + playerField + ", " + friendField + ", " + acceptedField + ") VALUES (?," +
			"?," +
			"?)";
	private static String deleteFriendRequest = "DELETE FROM " + tableName + " WHERE " + playerField + "=? AND " + friendField + "=?";

	public FriendSQL(SqlConfiguration sqlConfig) {
		super(sqlConfig.getHost(), sqlConfig.getPort(), sqlConfig.getDatabase(), sqlConfig.getUsername(), sqlConfig.getPassword());
		this.creationStatement = creationStatement.replace("[DB]", sqlConfig.getDatabase());
		execute(creationStatement);
	}

	public boolean hasData(String playerName) {
		PreparedStatement preparedStatement = prepareStatement(getPlayerDataStatement);
		boolean hasData = false;
		try {
			preparedStatement.setString(1, playerName);
			hasData = preparedStatement.executeQuery().next();
		} catch (Exception Ex) {
			Ex.printStackTrace();
		} finally {
			close(preparedStatement);
		}
		return hasData;
	}

	public List<Friend> getFriends(String playerName) {
		List<Friend> playerFriends = new ArrayList<>();
		PreparedStatement preparedStatement = prepareStatement(getPlayerDataStatement);
		if (this.hasData(playerName)) {
			try {
				preparedStatement.setString(1, playerName);
				ResultSet playerData = preparedStatement.executeQuery();
				while (playerData.next()) {
					playerFriends.add(new Friend(playerName, playerData.getString(friendField), playerData.getBoolean(acceptedField)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
		return playerFriends;
	}

	public List<Friend> getUnacceptedFriends(String playerName) {
		List<Friend> unacceptedFriends = new ArrayList<>();
		for (Friend friend : getFriends(playerName)) {
			if (!friend.isAccepted()) {
				unacceptedFriends.add(friend);
			}
		}
		return unacceptedFriends;
	}

	public boolean isPlayerFriendsWith(String playerName, String friendName) {
		PreparedStatement preparedStatement = prepareStatement(getFriendsStatusStatement);
		boolean isAccepted = false;
		if (this.hasData(playerName)) {
			try {
				preparedStatement.setString(1, playerName);
				preparedStatement.setString(2, friendName);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					isAccepted = resultSet.getBoolean(acceptedField);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
		return isAccepted;
	}

	public boolean hasFriendRequest(String playerName, String friendName) {
		PreparedStatement preparedStatement = prepareStatement(hasRequestStatement);
		boolean hasRequest = false;
		if (this.hasData(playerName)) {
			try {
				//Player we're checking requests for
				preparedStatement.setString(1, playerName);
				//Players we're checking requests from
				preparedStatement.setString(2, friendName);
				//Check for friend requests that havn't been accepted yet
				preparedStatement.setBoolean(3, false);
				hasRequest = preparedStatement.executeQuery().next();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
		return hasRequest;
	}

	public FriendStatus acceptFriendRequest(String playerName, String friendName) {
		PreparedStatement preparedStatement = prepareStatement(acceptFriendStatement);
		FriendStatus friendStatus = FriendStatus.NO_REQUEST;
		if (hasFriendRequest(playerName, friendName) && hasFriendRequest(friendName, playerName)) {
			try {
				//Player to Friend statement
				preparedStatement.setBoolean(1, true);
				preparedStatement.setString(2, playerName);
				preparedStatement.setString(3, friendName);
				preparedStatement.addBatch();
				//Friend to player statement
				preparedStatement.setBoolean(1, true);
				preparedStatement.setString(2, friendName);
				preparedStatement.setString(3, playerName);
				preparedStatement.addBatch();
				//Execute the batch statements
				preparedStatement.executeBatch();
				friendStatus = FriendStatus.ACCEPTED;
			} catch (Exception ex) {
				ex.printStackTrace();
				friendStatus = FriendStatus.ERROR;
			} finally {
				close(preparedStatement);
			}
		} else {
			friendStatus = FriendStatus.NO_REQUEST;
		}
		return friendStatus;
	}

	public FriendStatus insertFriendRequest(String playerSending, String friendRequested) {
		PreparedStatement preparedStatement = prepareStatement(insertFriendRequest);
		FriendStatus friendStatus;
		//Check if they're not already friends
		if (!this.hasFriendRequest(playerSending, friendRequested) && !this.hasFriendRequest(friendRequested, playerSending)) {
			if (!this.isPlayerFriendsWith(playerSending, friendRequested) && !this.isPlayerFriendsWith(friendRequested, playerSending)) {
				try {
					preparedStatement.setString(1, playerSending);
					preparedStatement.setString(2, friendRequested);
					preparedStatement.setBoolean(3, true);
					preparedStatement.addBatch();
					preparedStatement.setString(1, friendRequested);
					preparedStatement.setString(2, playerSending);
					preparedStatement.setBoolean(3, false);
					preparedStatement.addBatch();
					preparedStatement.executeBatch();
					friendStatus = FriendStatus.REQUESTED;
				} catch (Exception ex) {
					ex.printStackTrace();
					friendStatus = FriendStatus.ERROR;
				} finally {
					close(preparedStatement);
				}
			} else {
				friendStatus = FriendStatus.ALREADY_FRIENDS;
			}
		} else {
			friendStatus = FriendStatus.ALREADY_PENDING;
		}
		return friendStatus;
	}

	public void deleteFriendRequest(String playerToDeleteFrom, String deletingRequestOf) {
		PreparedStatement preparedStatement = prepareStatement(deleteFriendRequest);
		try {
			preparedStatement.setString(1, playerToDeleteFrom);
			preparedStatement.setString(2, deletingRequestOf);
			preparedStatement.addBatch();
			preparedStatement.setString(1, deletingRequestOf);
			preparedStatement.setString(2, playerToDeleteFrom);
			preparedStatement.addBatch();
			preparedStatement.executeBatch();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(preparedStatement);
		}
	}
}
