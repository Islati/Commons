package com.caved_in.commons.sql;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ServerDatabaseConnector extends DatabaseConnector {

    //Player Data Statements
    private static final String GET_PLAYER_DATA_STATEMENT = "SELECT server_players.player_money, server_prefixes.player_prefix, server_premium.premium FROM server_players, server_prefixes, server_premium WHERE server_players.player_id=? AND server_prefixes.player_id=? AND server_premium.player_id=?";
    private static final String PLAYER_HAS_DATA_UUID_STATEMENT = "SELECT * FROM server_players WHERE player_id=?";
    private static final String PLAYER_HAS_DATA_NAME_STATEMENT = "SELECT * FROM server_players WHERE player_name=?";
    //Default-data creation statements
    private static final String INSERT_PLAYER_TABLE_DATA = "INSERT INTO server_players (player_id, player_name, player_money) VALUES (?,?,?)";
    private static final String INSERT_PREMIUM_TABLE_DATA = "INSERT INTO server_premium (player_id,premium) VALUES (?,?)";
    private static final String INSERT_PREFIX_TABLE_DATA = "INSERT INTO server_prefixes (player_id, player_prefix) VALUES (?,?)";

    //Used by the console / api to perform actions to offline players
    private static final String SET_PLAYER_MONEY = "UPDATE server_players SET player_money=? WHERE player_id=?";
    private static final String GET_PLAYER_MONEY = "SELECT player_money FROM server_players WHERE player_id=?";

    //Server connection time
    private static final String INSERT_SERVER_CONNECTION_DATA = "INSERT INTO server_connections (player_id,player_name, player_ip, connect_time) VALUES (?,?,?,?)";

    //Friends table / friends list data statement
    private static final String GET_PLAYER_FRIENDS = "SELECT * FROM server_friends WHERE player_id=?";
    private static final String INSERT_FRIEND_REQUEST = "INSERT INTO server_friends (player_id,friend_id,friend_status) VALUES (?,?,?)";

    //Online status update query
    private static final String UPDATE_PLAYER_ONLINE_STATUS = "UPDATE server_online SET online=? AND svr_id=? WHERE player_id=?";
    private static final String GET_PLAYER_ONLINE_STATUS = "SELECT * FROM server_online WHERE player_id=?";
    private static final String INSERT_ONLINE_STATUS = "INSERT INTO server_online (player_id, online, svr_id) VALUES (?,?,?)";

    //Player premium status update statement
    private static final String UPDATE_PLAYER_PREMIUM_STATUS = "UPDATE server_premium SET premium=? WHERE player_id=?";

    private static final String SYNC_PLAYER_DATA = "UPDATE server_players,server_prefixes,server_premium SET server_players.player_name=?, server_players.player_money=?, server_prefixes.player_prefix=?, server_premium.premium=? WHERE server_players.player_id=? AND server_premium.player_id=? AND server_prefixes.player_id=?";

    /* Status tracker for the amount of players online, along with the maximum amount allowed */
    private static final String SYNC_SERVER_INFO = "UPDATE servers SET svr_player_count=?, svr_player_limit=? WHERE svr_name=?";
    private static final String INSERT_SERVER_INFO = "INSERT INTO servers (svr_name,svr_player_count,svr_player_limit,svr_online) VALUES (?,?,?,?)";
    private static final String RETRIEVE_SERVER_INFO = "SELECT * FROM servers WHERE svr_name=?";
    private static final String RETRIEVE_ALL_SERVER_INFO = "SELECT * FROM servers";
    private static final String UPDATE_SERVER_ONLINE_STATUS = "UPDATE servers SET svr_online=? WHERE svr_name=?";

    private static final String[] TABLE_CREATION_STATEMENTS = new String[]{
            "CREATE TABLE IF NOT EXISTS `server_online` (`player_id` varchar(36) NOT NULL, `online` tinyint(1) NOT NULL, `svr_id` int(11) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;",
            "CREATE TABLE IF NOT EXISTS `server_players` (`id` int(10) unsigned NOT NULL AUTO_INCREMENT, `player_id` varchar(36) NOT NULL, `player_name` varchar(16) NOT NULL, `player_money` int(11) NOT NULL DEFAULT '0', PRIMARY KEY (`id`)) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;",
            "CREATE TABLE IF NOT EXISTS `server_prefixes` (`player_id` varchar(36) NOT NULL, `player_prefix` text NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;",
            "CREATE TABLE IF NOT EXISTS `server_premium` (`player_id` varchar(36) NOT NULL, `premium` tinyint(1) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;",
            "CREATE TABLE IF NOT EXISTS `servers` (`svr_id` int(10) unsigned NOT NULL AUTO_INCREMENT, `svr_name` text CHARACTER SET utf8 NOT NULL, `svr_player_count` int(10) unsigned NOT NULL, `svr_player_limit` int(10) unsigned NOT NULL DEFAULT '100', `svr_online` tinyint(1) NOT NULL DEFAULT '0', PRIMARY KEY (`svr_id`), UNIQUE KEY `svr_id` (`svr_id`)) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;"
    };

    public ServerDatabaseConnector(String host, int port, String database, String username, String password) {
        super(host, port, database, username, password);
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

    public boolean hasOnlineStatusData(UUID id) {
        PreparedStatement statement = prepareStatement(GET_PLAYER_ONLINE_STATUS);
        boolean hasOnlineData = false;
        try {
            statement.setString(1, id.toString());
            ResultSet results = statement.executeQuery();
            hasOnlineData = results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return hasOnlineData;
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
                int playerCurrency = resultSet.getInt(1);
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

    public boolean updateOnlineStatus(UUID uniqueId, boolean isOnline) {

        //If the player doesn't have any data for them being online, then we're going to insert the default data for em.
        if (!hasOnlineStatusData(uniqueId)) {
            PreparedStatement insertStatement = prepareStatement(INSERT_ONLINE_STATUS);
            boolean inserted = false;
            try {
                insertStatement.setString(1, uniqueId.toString());
                insertStatement.setBoolean(2, isOnline);
                insertStatement.setInt(3, Commons.getServerId());
                insertStatement.execute();
                inserted = true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(insertStatement);
            }
            return inserted;
        }

        //On the other hand, if the player happens to have data, then we're going to just execute an update statement :)

        boolean dataUpdated = false;
        PreparedStatement statement = prepareStatement(UPDATE_PLAYER_ONLINE_STATUS);
        try {
            //Assign the values to the prepared statement
            statement.setBoolean(1, isOnline);
            statement.setInt(2, Commons.getServerId());
            statement.setString(3, uniqueId.toString());
            statement.execute();
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

    public void updatePlayerPremium(UUID playerId, boolean premium) throws SQLException {
        PreparedStatement statement = prepareStatement(UPDATE_PLAYER_PREMIUM_STATUS);
        String uniqueId = playerId.toString();
        try {
            statement.setBoolean(1, premium);
            statement.setString(2, uniqueId);
            statement.executeUpdate();
        } finally {
            close(statement);
        }
    }

    public void updatePlayerCount() {
        /*
        If no server info exists then we're gonna
		create the default data.
		 */
        if (!hasServerInfo()) {
            insertServerInfo();
        }

        PreparedStatement syncPlayerStatement = prepareStatement(SYNC_SERVER_INFO);

        try {
            syncPlayerStatement.setInt(1, Players.getOnlineCount());
            syncPlayerStatement.setInt(2, Bukkit.getServer().getMaxPlayers());
            syncPlayerStatement.setString(3, Commons.getInstance().getConfiguration().getServerName());
            syncPlayerStatement.executeUpdate();
            Chat.debug("Synchronized player count with the database");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(syncPlayerStatement);
        }
    }

    public boolean hasServerInfo() {
        PreparedStatement hasPlayerCount = prepareStatement(RETRIEVE_SERVER_INFO);
        boolean infoExists = false;
        try {
            hasPlayerCount.setString(1, Commons.getInstance().getConfiguration().getServerName());
            ResultSet results = hasPlayerCount.executeQuery();
            if (results.next()) {
                infoExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(hasPlayerCount);
        }
        return infoExists;
    }

    public void insertServerInfo() {
        PreparedStatement insertPlayerCountStatement = prepareStatement(INSERT_SERVER_INFO);

        try {
            insertPlayerCountStatement.setString(1, Commons.getInstance().getConfiguration().getServerName());
            insertPlayerCountStatement.setInt(2, Players.getOnlineCount());
            insertPlayerCountStatement.setInt(3, Bukkit.getServer().getMaxPlayers());
            insertPlayerCountStatement.setBoolean(4, true);
            insertPlayerCountStatement.execute();
            Chat.debug("Created default server-table info!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(insertPlayerCountStatement);
        }
    }

    public void updateServerOnlineStatus(boolean isOnline) {
        if (!hasServerInfo()) {
            insertServerInfo();
        }

        PreparedStatement statement = prepareStatement(UPDATE_SERVER_ONLINE_STATUS);
        String serverName = Commons.getInstance().getConfiguration().getServerName();

        try {
            statement.setBoolean(1, isOnline);
            statement.setString(2, serverName);
            statement.execute();
            Chat.debug("Updated the online status of " + serverName + " to " + String.valueOf(isOnline));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }
    }

    public boolean givePlayerMoney(UUID id, int amount) {
        if (!hasData(id)) {
            return false;
        }

        int money = getPlayerMoney(id);

        money += amount;

        return setPlayerMoney(id, money);
    }

    public boolean removePlayerMoney(UUID id, int amount) {
        //TODO Implement functionality.
        return false;
    }

    public int getPlayerMoney(UUID id) {
        int money = 0;
        PreparedStatement statement = prepareStatement(GET_PLAYER_MONEY);

        try {
            statement.setString(1, id.toString());

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                money = results.getInt("player_money");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return money;
    }

    /**
     * Set how much money the player with the given uuid will have.
     *
     * @param id     id of the player to set the money for.
     * @param amount amount of money the player will have.
     * @return true if the statement was executed without problem false otherwise.
     */
    public boolean setPlayerMoney(UUID id, int amount) {
        boolean executed = false;

        if (!hasData(id)) {
            return false;
        }

        PreparedStatement statement = prepareStatement(SET_PLAYER_MONEY);

        try {
            statement.setInt(1, amount);
            statement.setString(2, id.toString());

            statement.executeUpdate();
            executed = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return executed;
    }
}
