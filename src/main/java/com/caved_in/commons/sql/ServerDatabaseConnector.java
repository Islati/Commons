package com.caved_in.commons.sql;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentBuilder;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.friends.Friend;
import com.caved_in.commons.friends.FriendStatus;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
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
public class ServerDatabaseConnector extends DatabaseConnector {

	//Player Data Statements
	private static final String GET_PLAYER_DATA_STATEMENT = "SELECT " + PLAYER_TABLE + "." + PLAYER_CURRENCY + ", " +
			"" + PLAYER_PREFIX_TABLE + "." + PLAYER_NAME_PREFIX + ", " + PREMIUM_TABLE + "." + PREMIUM_STATUS
			+ " FROM " + PLAYER_PREFIX_TABLE + ", " + PREMIUM_TABLE + " WHERE " + PLAYER_PREFIX_TABLE + "." + PLAYER_UNIQUE_ID + "=? AND " + PREMIUM_TABLE + "" +
			"." + PREMIUM_STATUS + "=? AND " + PLAYER_TABLE + "." + PLAYER_UNIQUE_ID + "=?";

	private static final String PLAYER_HAS_DATA_UUID_STATEMENT = "SELECT * FROM " + PLAYER_TABLE + " WHERE " + PLAYER_UNIQUE_ID + "=?";
	private static final String PLAYER_HAS_DATA_NAME_STATEMENT = "SELECT * FROM " + PLAYER_TABLE + " WHERE " + PLAYER_NAME + "=?";
	//Default-data creation statements
	private static final String INSERT_PLAYER_TABLE_DATA = "INSERT INTO " + PLAYER_TABLE + " (" + PLAYER_UNIQUE_ID + ", " + PLAYER_NAME + ", " + PLAYER_CURRENCY + ") VALUES (?,?,?)";
	private static final String INSERT_PREMIUM_TABLE_DATA = "INSERT INTO " + PREMIUM_TABLE + " (" + PLAYER_UNIQUE_ID + ", " + PREMIUM_STATUS + ") VALUES (?,?)";
	private static final String INSERT_PREFIX_TABLE_DATA = "INSERT INTO " + PLAYER_PREFIX_TABLE + " (" + PLAYER_UNIQUE_ID + ", " + PLAYER_NAME_PREFIX + ") VALUES (?,?)";

	//Server connection time
	private static final String INSERT_SERVER_CONNECTION_DATA = "INSERT INTO " + SERVER_CONNECTION_TABLE + " (" + PLAYER_UNIQUE_ID + ", " + PLAYER_NAME + ", " + PLAYER_IP_ADDRESS + ", " + SERVER_CONNECT_TIME + ") VALUES (?,?,?,?)";

	//Punishments statements
	private static String RETRIEVE_ALL_PLAYER_PUNISHMENTS = "SELECT * FROM " + PUNISHMENTS_TABLE + " WHERE " + PLAYER_UNIQUE_ID + "=?";
	private static String RETRIEVE_ACTIVE_PLAYER_PUNISHMENTS = "SELECT * FROM " + PUNISHMENTS_TABLE + " WHERE " + PLAYER_UNIQUE_ID + "=? AND pardoned=0 AND " + PUNISHMENT_EXPIRATION_TIME + " > ?";
	private static String RETRIEVE_MOST_RECENT_PLAYER_PUNISHMENT = "SELECT Max(pun_expires) as expires, pun_reason, pun_issued, pun_giver_id FROM server_punishments WHERE player_id=? and pun_type=?";
	private static String PARDON_ACTIVE_PUNISHMENTS = "UPDATE server_punishments SET pardoned=1 WHERE player_id=? AND pardoned=0";
	private static String PARDON_ACTIVE_PUNISHMENTS_TYPE = "UPDATE server_punishments SET pardoned=1 WHERE pun_type=? AND player_id=? AND pardoned=0";
	//Friends table / friends list data statement
	private static String GET_PLAYER_FRIENDS = "SELECT * FROM " + FRIENDS_TABLE + " WHERE " + PLAYER_UNIQUE_ID + "=?";
	private static String INSERT_FRIEND_REQUEST = "INSERT INTO " + FRIENDS_TABLE + " (" + PLAYER_UNIQUE_ID + "," + FRIEND_USER_ID + ", " + FRIEND_STATUS + ") VALUES (?,?,?)";
	private static String UPDATE_FRIEND_REQUEST = "UPDATE " + FRIENDS_TABLE + " SET " + FRIEND_STATUS + "=? WHERE " + PLAYER_UNIQUE_ID + "=? AND " + FRIEND_USER_ID + "=?";

	//Online status update query
	private static final String UPDATE_PLAYER_ONLINE_STATUS = "REPLACE INTO " + ONLINE_TABLE + " SET " + ONLINE_STATUS + "=? AND " + SERVER_ID + "=? WHERE " + PLAYER_UNIQUE_ID + "=?";

	//Player premium status update statement
	private static final String UPDATE_PLAYER_PREMIUM_STATUS = "REPLACE INTO " + PREMIUM_TABLE + " SET " + PLAYER_UNIQUE_ID + "=? AND " + PREMIUM_STATUS + "=? WHERE " + PLAYER_UNIQUE_ID + "=?";

	private static final String SYNC_PLAYER_DATA = "UPDATE server_players,server_prefixes,server_premium SET server_players.player_name=?, server_players.player_money=?, server_prefixes.player_prefix=?, server_premium.premium=? WHERE server_players.player_id=? AND server_premium.player_id=? AND server_prefixes.player_id=?";

	public ServerDatabaseConnector(SqlConfiguration sqlConfiguration) {
		super(sqlConfiguration);
	}

	public PlayerWrapper getPlayerWrapper(Player player) {
		return getPlayerWrapper(player.getUniqueId());
	}

	public boolean hasData(UUID playerId) {
		PreparedStatement statement = prepareStatement(PLAYER_HAS_DATA_UUID_STATEMENT);
		boolean playerHasData = false;
		try {
			statement.setString(1, playerId.toString());
			ResultSet resultSet = statement.executeQuery();
			playerHasData = resultSet.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(statement);
		}
		return playerHasData;
	}

	public boolean hasData(String playerName) {
		PreparedStatement statement = prepareStatement(PLAYER_HAS_DATA_NAME_STATEMENT);
		boolean playerHasData = false;
		try {
			statement.setString(1, playerName);
			ResultSet resultSet = statement.executeQuery();
			playerHasData = resultSet.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(statement);
		}
		return playerHasData;
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

	public boolean syncPlayerWrapperData(PlayerWrapper playerWrapper) {
		boolean status = false;
		PreparedStatement statement = prepareStatement(SYNC_PLAYER_DATA);
		try {
			statement.setString(1, playerWrapper.getName());
			statement.setDouble(2, playerWrapper.getCurrency());
			statement.setString(3, playerWrapper.getPrefix());
			statement.setBoolean(4, playerWrapper.isPremium());
			String uuid = playerWrapper.getId().toString();
			statement.setString(5, uuid);
			statement.setString(6, uuid);
			statement.setString(7, uuid);
			statement.executeUpdate();
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement);
		}
		return status;
	}

	public void insertDefaultData(Player player) {
		insertDefaultData(player.getUniqueId(), player.getName());
	}

	public void insertDefaultData(UUID playerId, String name) {
		String uniqueId = playerId.toString();
		PreparedStatement playerTableStatement = prepareStatement(INSERT_PLAYER_TABLE_DATA);
		PreparedStatement premiumTableStatement = prepareStatement(INSERT_PREMIUM_TABLE_DATA);
		PreparedStatement prefixTableStatement = prepareStatement(INSERT_PREFIX_TABLE_DATA);

		try {
			//Insert the data for the player table
			playerTableStatement.setString(1, uniqueId);
			playerTableStatement.setString(2, name);
			playerTableStatement.setDouble(3, 0);
			playerTableStatement.execute();
			//Insert the data for the premium table
			premiumTableStatement.setString(1, uniqueId);
			premiumTableStatement.setBoolean(2, false);
			premiumTableStatement.execute();
			//Insert the data for the prefix table
			prefixTableStatement.setString(1, uniqueId);
			prefixTableStatement.setString(2, Players.DEFAULT_PREFIX);
			prefixTableStatement.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			//Close all our prepared statements.
			close(playerTableStatement, prefixTableStatement, premiumTableStatement);
		}
	}

	public Set<Punishment> getActivePunishments(UUID playerId) {
		long timeStamp = System.currentTimeMillis();
		PreparedStatement statement = prepareStatement(RETRIEVE_ALL_PLAYER_PUNISHMENTS);
		Set<Punishment> punishments = new HashSet<>();
		try {
			statement.setString(1, playerId.toString());
			statement.setLong(2, timeStamp);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				//Build the punishment and add it to the set of punishments for the player
				Punishment punishment = new PunishmentBuilder()
						.withType(PunishmentType.getPunishmentType(resultSet.getString(PUNISHMENT_TYPE.toString())))
						.withIssuer(resultSet.getString(PUNISHMENT_ISSUER_UID.toString()))
						.withReason(resultSet.getString(PUNISHMENT_REASON.toString()))
						.issuedOn(resultSet.getLong(PUNISHMENT_ISSUED_TIME.toString()))
						.expiresOn(resultSet.getLong(PUNISHMENT_EXPIRATION_TIME.toString()))
						.build();
				punishments.add(punishment);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(statement);
		}
		return punishments;
	}

	public Punishment getMostRecentPunishment(UUID uniqueId, PunishmentType type) {
		PreparedStatement statement = prepareStatement(RETRIEVE_MOST_RECENT_PLAYER_PUNISHMENT);
		Punishment punishment = null;
		try {
			statement.setString(1, uniqueId.toString());
			statement.setInt(2, type.getId());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				punishment = new PunishmentBuilder().withType(type).withReason(resultSet.getString("pun_reason")).withIssuer(resultSet.getString("pun_giver_id")).issuedOn(resultSet.getLong("pun_issued")).expiresOn(resultSet.getLong("expires")).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement);
		}
		return punishment;
	}

	public void pardonActivePunishments(UUID playerId) {
		PreparedStatement statement = prepareStatement(PARDON_ACTIVE_PUNISHMENTS);
		try {
			statement.setString(1, playerId.toString());
			statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(statement);
		}
	}

	public void pardonActivePunishments(UUID playerId, PunishmentType type) {
		PreparedStatement statement = prepareStatement(PARDON_ACTIVE_PUNISHMENTS_TYPE);
		try {
			statement.setInt(1, type.getId());
			statement.setString(2, playerId.toString());
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(statement);
		}
	}

	public boolean hasActivePunishment(UUID playerId) {
		return getActivePunishments(playerId).size() > 0;
	}

	public boolean hasActivePunishment(UUID playerId, PunishmentType punishmentType) {
		for (Punishment punishment : getActivePunishments(playerId)) {
			if (punishmentType == punishment.getPunishmentType()) {
				return true;
			}
		}
		return false;
	}

	public void updateFriendRequest(UUID playerId, UUID friendId, FriendStatus status) {
		PreparedStatement statement = prepareStatement(UPDATE_FRIEND_REQUEST);
		try {
			//Build the statement to update the request for the player
			statement.setInt(1, status.getId());
			statement.setString(2, playerId.toString());
			statement.setString(3, friendId.toString());
			statement.addBatch();
			statement.clearParameters();
			//Build the statement to update the request for the friend
			statement.setInt(1, status.getId());
			statement.setString(2, friendId.toString());
			statement.setString(3, playerId.toString());
			statement.addBatch();
			//Execute the batch statements
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement);
		}
	}

	public void updateFriendRequest(Friend friend, FriendStatus friendStatus) {
		updateFriendRequest(friend.getPlayerId(), friend.getFriendId(), friendStatus);
	}

	public void insertFriendRequest(UUID playerId, UUID friendId) {
		PreparedStatement statement = prepareStatement(INSERT_FRIEND_REQUEST);
		try {
			//Build the statement to insert the request for the player
			statement.setString(1, playerId.toString());
			statement.setString(2, friendId.toString());
			statement.setInt(3, FriendStatus.REQUESTED.getId());
			statement.addBatch();
			//Clear the parameters for the next statement
			statement.clearParameters();
			//Build the statement to insert the request for the player being requested
			statement.setString(1, friendId.toString());
			statement.setString(2, playerId.toString());
			statement.setInt(3, FriendStatus.REQUESTED.getId());
			statement.addBatch();
			//Execute the statements
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement);
		}
	}

	public Set<Friend> getFriends(Player player) {
		return getFriends(player.getUniqueId());
	}

	public Set<Friend> getFriends(UUID playerId) {
		Set<Friend> friends = new HashSet<>();
		PreparedStatement statement = prepareStatement(GET_PLAYER_FRIENDS);
		try {
			statement.setString(1, playerId.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Friend friend;
				UUID friendId = UUID.fromString(resultSet.getString(FRIEND_USER_ID.toString()));
				friend = new Friend(playerId, friendId);
				friends.add(friend);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return friends;
	}

	public boolean hasFriends(UUID playerId) {
		return getFriends(playerId).size() > 0;
	}

	public boolean hasFriends(Player player) {
		return hasFriends(player.getUniqueId());
	}

	public boolean updateOnlineStatus(UUID uniqueId, boolean isOnline) {
		boolean dataUpdated = false;
		PreparedStatement statement = prepareStatement(UPDATE_PLAYER_ONLINE_STATUS);
		try {
			//Assign the values to the prepared statement
			statement.setBoolean(1, isOnline);
			statement.setInt(2, Commons.getServerId());
			statement.setString(3, uniqueId.toString());
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
		return updateOnlineStatus(playerWrapper.getId(), isOnline);
	}

	public boolean updatePlayerPremium(UUID playerId, boolean premium) {
		PreparedStatement statement = prepareStatement(UPDATE_PLAYER_PREMIUM_STATUS);
		String uniqueId = playerId.toString();
		boolean status = false;
		try {
			statement.setString(1, uniqueId);
			statement.setBoolean(2, premium);
			statement.setString(3, uniqueId);
			status = statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement);
		}
		return status;
	}

	public void insertPlayerBan(PunishmentType punishmentType, UUID playerId, String giverId, String reason, long expires) {
		PreparedStatement statement = prepareStatement("INSERT INTO server_punishments (pun_type, player_id, pun_giver_id, pun_issued, pun_expires, pun_reason, pardoned) VALUES (?,?,?,?,?,?,?)");
		try {
			statement.setInt(1, punishmentType.getId());
			statement.setString(2, playerId.toString());
			statement.setString(3, giverId);
			statement.setLong(4, expires);
			statement.setString(5, reason);
			statement.setBoolean(6, false);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement);
		}
	}

	public void insertPlayerBan(Punishment punishment, UUID playerId) {
		insertPlayerBan(punishment.getPunishmentType(), playerId, punishment.getIssuer().toString(), punishment.getReason(), punishment.getExpiryTime());
	}
}
