package com.caved_in.commons.sql;

import com.caved_in.commons.Commons;
import com.google.common.base.Stopwatch;

import java.sql.*;

public class SQL {

	private Connection sqlConnection = null;
	private String host, port, database, username, password;

	/**
	 * @param host
	 * @param port
	 * @param database
	 * @param username
	 * @param password
	 */
	public SQL(String host, String port, String database, String username, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		//Initialize our connection
		initConnection();
	}

	private void initConnection() {
		Stopwatch stopwatch = new Stopwatch();
		try {
			Commons.messageConsole("Attempting to establish a connection the MySQL server!");
			stopwatch.start();
			Class.forName("com.mysql.jdbc.Driver");
			sqlConnection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
			stopwatch.stop();
			Commons.messageConsole("Connection to MySQL server established! (" + host + ":" + port + ")");
			Commons.messageConsole("Connection took " + stopwatch + "ms!");
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

	public void refreshConnection() {

		PreparedStatement st;
		ResultSet valid = null;
		try {
			st = sqlConnection.prepareStatement("SELECT * FROM players");
			valid = st.executeQuery();
			if (valid.next()) {
				return;
			}
		} catch (SQLException e2) {
			System.out.println("Connection is idle or terminated. Reconnecting...");
		} finally {
			this.closeQuietly(valid);
		}

		initConnection();
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

	public boolean execute(String sql) {
		boolean st = false;
		try {
			Statement statement = sqlConnection.createStatement();
			st = statement.execute(sql);
			statement.close();
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return st;
	}

	public ResultSet executeQuery(String sql) {
		ResultSet st = null;
		try {
			Statement statement = sqlConnection.createStatement();
			st = statement.executeQuery(sql);
			statement.close();
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return st;
	}

	public ResultSet executeQueryOpen(String sql) {
		ResultSet st = null;
		try {
			Statement statement = sqlConnection.createStatement();
			st = statement.executeQuery(sql);
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return st;
	}

	public int executeUpdate(String sql) {
		int st = 0;
		try {
			Statement statement = sqlConnection.createStatement();
			st = statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return st;
	}

	public boolean createTable(String TableName, String[] Values) {
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TableName + " (");
		for (int i = 0; i < Values.length; i++) {
			if (i == (Values.length - 1)) {
				sql.append(Values[i]);
			} else {
				sql.append(Values[i] + ", ");
			}
		}
		sql.append(");");

		try {
			Statement statement = sqlConnection.createStatement();
			statement.executeUpdate(sql.toString());
			return true;
		} catch (SQLException Ex) {
			Ex.printStackTrace();
			return false;
		}
	}

	public void deleteTable(String TableName) {
		String sql = "DROP TABLE IF EXISTS " + TableName;

		try {
			Statement statement = sqlConnection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
	}

	public void deleteAllFromTable(String TableName, String Column, String Value) {
		String Query = "DELETE FROM " + TableName + " WHERE " + Column + "='" + Value + "';";
		try {
			Statement statement = sqlConnection.createStatement();
			statement.executeUpdate(Query);
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
	}

	public ResultSet getRowsByColumn(String Table, String Column, String Value) {
		ResultSet rs = this.executeQuery("SELECT * FROM " + Table + " WHERE " + Column + "= '" + Value + "';");
		return rs;
	}

	public String getValue(ResultSet Set, String Column) {
		try {
			return Set.getString(Column);
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return null;
	}

	public int getRowCount(String Table) {
		ResultSet rs = this.executeQuery("SELECT * FROM " + Table);
		int count = 0;
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException Ex) {
			Ex.printStackTrace();
		}
		return count;
	}

	public Connection getSqlConnection() {
		return sqlConnection;
	}

	public boolean getBoolean(ResultSet SQL, String Field) {
		try {
			if (!SQL.isClosed()) {
				if (SQL.next()) {
					boolean Return = SQL.getBoolean(Field);
					SQL.close();
					return Return;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (SQLException Ex) {
			Ex.printStackTrace();
			return false;
		}
	}

	public Object getData(ResultSet SQL, String Column) {
		try {
			if (!SQL.isClosed()) {
				if (SQL.next()) {
					Object Return = SQL.getObject(Column);
					SQL.close();
					return Return;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (SQLException Ex) {
			Ex.printStackTrace();
			return null;
		}
	}

	public enum Field {
		Boolean, Long, Double, String
	}

}