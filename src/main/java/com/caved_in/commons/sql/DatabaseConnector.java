package com.caved_in.commons.sql;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.SqlConfiguration;
import com.google.common.base.Stopwatch;

import java.sql.*;

public abstract class DatabaseConnector implements TableConnector {

	private Connection sqlConnection = null;
	private SqlConfiguration config;

	public DatabaseConnector(SqlConfiguration sqlConfiguration) {
		this.config = sqlConfiguration;
		initConnection();

	}

	private void initConnection() {
		Stopwatch stopwatch = Stopwatch.createStarted();
		try {
			Chat.debug("Attempting to establish a connection the MySQL server!");
			Class.forName("com.mysql.jdbc.Driver");
			sqlConnection = DriverManager.getConnection("jdbc:mysql://" + config.getHost() + ":" + config.getPort() + "/" + config.getDatabase() + "?autoReconnect=true", config.getUsername(), config.getPassword());
			stopwatch.stop();
			Chat.debug("Connection to MySQL server established! (" + config.getHost() + ":" + config.getPort() + ")");
			Chat.debug("Connection took " + stopwatch + "ms!");
		} catch (SQLException e) {
			Commons.messageConsole("Could not connect to MySQL server! because: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			Commons.messageConsole("JDBC Driver not found!");
		}
	}

	public PreparedStatement prepareStatement(String sqlStatement) {
		try {
			return sqlConnection.prepareStatement(sqlStatement);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isConnected() {
		return sqlConnection != null;
	}

	public void closeConnection() {
		try {
			sqlConnection.close();
		} catch (SQLException Ex) {
			System.out.println("Couldn't close Connection");
		}
	}

	public void closeQuietly(ResultSet rs) {
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) rs.getStatement();
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// Ignore... nothing we can do about this here
				}
			}

			if (ps != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// Ignore... nothing we can do about this here
				}
			}
		}
	}

	public Connection getSqlConnection() {
		return sqlConnection;
	}

	public boolean close(PreparedStatement preparedStatement) {
		if (preparedStatement == null) {
			return false;
		}

		boolean closed = false;
		try {
			preparedStatement.close();
			closed = preparedStatement.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return closed;
	}

	public boolean[] close(PreparedStatement... statements) {
		boolean[] closed = new boolean[statements.length];
		for (int i = 0; i < statements.length; i++) {
			closed[i] = close(statements[i]);
		}
		return closed;
	}

	public SqlConfiguration getConfig() {
		return config;
	}
}