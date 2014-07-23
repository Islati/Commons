package com.caved_in.commons.sql;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentBuilder;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.friends.Friend;
import com.caved_in.commons.friends.FriendStatus;
import com.caved_in.commons.player.MinecraftPlayer;
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
	private static final String GET_PLAYER_DATA_STATEMENT = "SELECT server_players.player_money, server_prefixes.player_prefix, server_premium.premium FROM server_players, server_prefixes, server_premium WHERE server_players.player_id=? AND server_prefixes.player_id=? AND server_premium.player_id=?";
	private static final String PLAYER_HAS_DATA_UUID_STATEMENT = "SELECT * FROM server_players WHERE player_id=?";
	private static final String PLAYER_HAS_DATA_NAME_STATEMENT = "SELECT * FROM server_players WHERE player_name=?";
	//Default-data creation statements
	private static final String INSERT_PLAYER_TABLE_DATA = "INSERT INTO server_players (player_id, player_name, player_money) VALUES (?,?,?)";
	private static final String INSERT_PREMIUM_TABLE_DATA = "INSERT INTO server_premium (player_id,premium) VALUES (?,?)";
	private static final String INSERT_PREFIX_TABLE_DATA = "INSERT INTO server_prefixes (player_id, player_prefix) VALUES (?,?)";

	//Server connection time
	private static final String INSERT_SERVER_CONNECTION_DATA = "INSERT INTO server_connections (player_id,player_name, player_ip, connect_time) VALUES (?,?,?,?)";

	//Punishments statements
	private static String RETRIEVE_ALL_PLAYER_PUNISHMENTS = "SELECT * FROM server_punishments WHERE player_id=?";
	private static String RETRIEVE_ACTIVE_PLAYER_PUNISHMENTS = "SELECT * FROM server_punishments WHERE player_id=? AND pardoned=0 AND pun_expires > ?";
	private static String RETRIEVE_MOST_RECENT_PLAYER_PUNISHMENT = "SELECT pun_expires, pun_reason, pun_issued, pun_giver_id FROM server_punishments WHERE player_id=? and pun_type=? ORDER BY pun_expires DESC LIMIT 1";
	private static String PARDON_ACTIVE_PUNISHMENTS = "UPDATE server_punishments SET pardoned=1 WHERE player_id=?";
	private static String PARDON_ACTIVE_PUNISHMENTS_TYPE = "UPDATE server_punishments SET pardoned=1 WHERE pun_type=? AND player_id=? AND pardoned=0";
	//Friends table / friends list data statement
	private static String GET_PLAYER_FRIENDS = "SELECT * FROM server_friends WHERE player_id=?";
	private static String INSERT_FRIEND_REQUEST = "INSERT INTO server_friends (player_id,friend_id,friend_status) VALUES (?,?,?)";
	private static String UPDATE_FRIEND_REQUEST = "UPDATE server_friends SET friend_status=? WHERE player_id=? AND friend_id=?";

	//Online status update query
	private static final String UPDATE_PLAYER_ONLINE_STATUS = "REPLACE INTO server_online SET online=? AND svr_id=? WHERE player_id=?";

	//Player premium status update statement
	private static final String UPDATE_PLAYER_PREMIUM_STATUS = "REPLACE INTO server_premium SET player_id=? AND premium=? WHERE player_id=?";

	private static final String SYNC_PLAYER_DATA = "UPDATE server_players,server_prefixes,server_premium SET server_players.player_name=?, server_players.player_money=?, server_prefixes.player_prefix=?, server_premium.premium=? WHERE server_players.player_id=? AND server_premium.player_id=? AND server_prefixes.player_id=?";

	private static final String[] TABLE_CREATION_STATEMENTS = new String[]{
			"CREATE TABLE IF NOT EXISTS `server_online` (`player_id` varchar(36) NOT NULL, `online` tinyint(1) NOT NULL, `svr_id` int(11) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;",
			"CREATE TABLE IF NOT EXISTS `server_players` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT, `player_id` varchar(36) NOT NULL, `player_name` varchar(16) NOT NULL, `player_money` double NOT NULL DEFAULT '0', PRIMARY KEY (`id`)) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;",
			"CREATE TABLE IF NOT EXISTS `server_prefixes` (`player_id` varchar(36) NOT NULL, `player_prefix` text NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;",
			"CREATE TABLE IF NOT EXISTS `server_premium` (`player_id` varchar(36) NOT NULL, `premium` tinyint(1) NOT NULL DEFAULT '0') ENGINE=InnoDB DEFAULT CHARSET=latin1;",
			"CREATE TABLE IF NOT EXISTS `server_punishments` (`id` int(11) NOT NULL AUTO_INCREMENT, `pun_type` int(11) NOT NULL, `player_id` varchar(36) NOT NULL, `pun_giver_id` varchar(36) NOT NULL, `pun_issued` bigint(20) NOT NULL, `pun_expires` bigint(20) NOT NULL, `pun_reason` text NOT NULL, `pardoned` tinyint(1) NOT NULL, KEY `id` (`id`)) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;"
	};

	public ServerDatabaseConnector(SqlConfiguration sqlConfiguration) {
		super(sqlConfiguration);
		executeCreationStatements();
	}

	private void executeCreationStatements() {
		for (String statement : TABLE_CREATION_STATEMENTS) {
			PreparedStatement preparedStatement = prepareStatement(statement);
			try {
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(preparedStatement);
			}
		}
	}

	public MinecraftPlayer getPlayerWrapper(Player player) {
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

	public MinecraftPlayer getPlayerWrapper(UUID playerId) {
		MinecraftPlayer minecraftPlayer = null;
		PreparedStatement statement = prepareStatement(GET_PLAYER_DATA_STATEMENT);
		try {
			//Assign the first 3 variables in the statement to the unique ID (user id)
			String uidString = playerId.toString();
			statement.setString(1, uidString);
			statement.setString(2, uidString);
			statement.setString(3, uidString);

			//Execute the query and get the resultset from it
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				//Get the players currency, prefix, and premium status
				double playerCurrency = resultSet.getDouble(1);
				String playerPrefix = resultSet.getString(2);
				boolean playerPremium = resultSet.getBoolean(3);
				//Create a new player wrapper and assign the variables pulled from the database
				minecraftPlayer = new MinecraftPlayer(playerId);
				minecraftPlayer.setCurrency(playerCurrency);
				minecraftPlayer.setPremium(playerPremium);
				minecraftPlayer.setPrefix(playerPrefix);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(statement);
		}
		return minecraftPlayer;
	}

	public boolean syncPlayerWrapperData(MinecraftPlayer minecraftPlayer) {
		boolean status = false;
		PreparedStatement statement = prepareStatement(SYNC_PLAYER_DATA);
		try {
			statement.setString(1, minecraftPlayer.getName());
			statement.setDouble(2, minecraftPlayer.getCurrency());
			statement.setString(3, minecraftPlayer.getPrefix());
			statement.setBoolean(4, minecraftPlayer.isPremium());
			String uuid = minecraftPlayer.getId().toString();
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
		PreparedStatement statement = prepareStatement(RETRIEVE_ACTIVE_PLAYER_PUNISHMENTS);
		Set<Punishment> punishments = new HashSet<>();
		try {
			statement.setString(1, playerId.toString());
			statement.setLong(2, timeStamp);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				//Build the punishment and add it to the set of punishments for the player
				Punishment punishment = new PunishmentBuilder()
						.withType(PunishmentType.getPunishmentType(resultSet.getInt(PUNISHMENT_TYPE.toString())))
						.withIssuer(UUID.fromString(resultSet.getString(PUNISHMENT_ISSUER_UID.toString())))
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
				punishment = new PunishmentBuilder().withType(type).withReason(resultSet.getString("pun_reason")).withIssuer(UUID.fromString(resultSet.getString("pun_giver_id"))).issuedOn(resultSet.getLong("pun_issued")).expiresOn(resultSet.getLong("pun_expires")).build();
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
			Commons.messageConsole("Executed pardon statement for " + playerId.toString());
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

	public boolean updateOnlineStatus(MinecraftPlayer minecraftPlayer, boolean isOnline) {
		return updateOnlineStatus(minecraftPlayer.getId(), isOnline);
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
			statement.setLong(4, System.currentTimeMillis());
			statement.setLong(5, expires);
			statement.setString(6, reason);
			statement.setBoolean(7, false);
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
