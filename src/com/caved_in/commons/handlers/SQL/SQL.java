package com.caved_in.commons.handlers.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL
{

	private Connection Connection = null;
	private String host, port, database, username, password;

	/**
	 * 
	 * @param host
	 * @param port
	 * @param database
	 * @param username
	 * @param password
	 */
	public SQL(String host, String port, String database, String username, String password)
	{

		long start = 0;
		long end = 0;

		try
		{
			start = System.currentTimeMillis();
			System.out.println("Attempting to establish a connection the MySQL server!");
			Class.forName("com.mysql.jdbc.Driver");
			Connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
			end = System.currentTimeMillis();
			System.out.println("Connection to MySQL server established! (" + host + ":" + port + ")");
			System.out.println("Connection took " + ((end - start)) + "ms!");
		}
		catch (SQLException e)
		{
			System.out.println("Could not connect to MySQL server! because: " + e.getMessage());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("JDBC Driver not found!");
		}
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	/**
	 * Check if the instanced SQL Connection is connected
	 * 
	 * @return True if the Connection is active, false otherwise
	 */
	public boolean isConnected()
	{
		if (Connection != null)
		{
			return true;
		}
		return false;
	}

	/**
	 * close the instanced SQL Connection
	 */
	public void closeConnection()
	{
		try
		{
			Connection.close();
		}
		catch (SQLException Ex)
		{
			System.out.println("Couldn't close Connection");
		}
	}

	public void refreshConnection()
	{

		PreparedStatement st = null;
		ResultSet valid = null;
		try
		{
			st = Connection.prepareStatement("SELECT 1 FROM Players");
			valid = st.executeQuery();
			if (valid.next())
				return;
		}
		catch (SQLException e2)
		{
			System.out.println("Connection is idle or terminated. Reconnecting...");
		}
		finally
		{
			this.closeQuietly(valid);
		}

		long start = 0;
		long end = 0;

		try
		{
			start = System.currentTimeMillis();
			System.out.println("Attempting to establish a connection the MySQL server!");
			Class.forName("com.mysql.jdbc.Driver");
			Connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			end = System.currentTimeMillis();
			System.out.println("Connection to MySQL server established! (" + host + ":" + port + ")");
			System.out.println("Connection took " + ((end - start)) + "ms!");
		}
		catch (SQLException e)
		{
			System.out.println("Could not connect to MySQL server! because: " + e.getMessage());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("JDBC Driver not found!");
		}
	}

	public void closeQuietly(ResultSet rs)
	{
		PreparedStatement ps = null;
		try
		{
			ps = (PreparedStatement) rs.getStatement();
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// Ignore... nothing we can do about this here
				}
			}

			if (ps != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// Ignore... nothing we can do about this here
				}
			}
		}
	}

	/**
	 * Executes a generic SQL Statement
	 * 
	 * @param sql
	 *            Statement to execute
	 * @return True if true, false otherwise
	 */
	public boolean execute(String sql)
	{
		boolean st = false;
		try
		{
			Statement statement = Connection.createStatement();
			st = statement.execute(sql);
			statement.close();
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
		}
		return st;
	}

	/**
	 * Executes an SQL Query and returns the ResultSet then closes it after
	 * executing Note that this method and call doesn't allow the statements
	 * Values to be retrieved nor modified after calling As its statement is
	 * closed. To modify or use Values in a ResultSet, call the executeQueryOpen
	 * method
	 * 
	 * @param sql
	 *            Statement to query
	 * @return Resultset of the pulled data, or null (if failed)
	 */
	public ResultSet executeQuery(String sql)
	{
		ResultSet st = null;
		try
		{
			Statement statement = Connection.createStatement();
			st = statement.executeQuery(sql);
			statement.close();
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
		}
		return st;
	}

	/**
	 * Executes an SQL Query and returns the ResultSet without closing it after
	 * executing; Closing is to be done in the methods using this method
	 * 
	 * @param sql
	 *            Statement to query
	 * @return Resultset of the pulled data, or null (if failed)
	 */
	public ResultSet executeQueryOpen(String sql)
	{
		ResultSet st = null;
		try
		{
			Statement statement = Connection.createStatement();
			st = statement.executeQuery(sql);
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
		}
		return st;
	}

	/**
	 * Sends an SQL Statement for execution then closes the statement afterwards
	 * 
	 * @param sql
	 *            Statement to execute
	 * @return either (1) the row count for SQL Data Manipulation Language (DML)
	 *         statements or (2) 0 for SQL statements that return nothing [Same
	 *         as statement.executeupdates description]
	 */
	public int executeUpdate(String sql)
	{
		int st = 0;
		try
		{
			Statement statement = Connection.createStatement();
			st = statement.executeUpdate(sql);
			statement.close();
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
		}
		return st;
	}

	/**
	 * Creates a Table if it doesn't exist in the instanced database
	 * 
	 * @param TableName
	 *            Name of Table to create within the database
	 * @param Values
	 *            Value to put in said Table if created
	 * @return True if the Table was created, false otherwise
	 */
	public boolean createTable(String TableName, String[] Values)
	{
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TableName + " (");
		for (int i = 0; i < Values.length; i++)
		{
			if (i == (Values.length - 1))
			{
				sql.append(Values[i]);
			}
			else
			{
				sql.append(Values[i] + ", ");
			}
		}
		sql.append(");");

		try
		{
			Statement statement = Connection.createStatement();
			statement.executeUpdate(sql.toString());
			return true;
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
			return false;
		}
	}

	public void deleteTable(String TableName)
	{
		String sql = "DROP TABLE IF EXISTS " + TableName;

		try
		{
			Statement statement = Connection.createStatement();
			statement.executeUpdate(sql);
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
		}
	}

	public void deleteAllFromTable(String TableName, String Column, String Value)
	{
		String Query = "DELETE FROM " + TableName + " WHERE " + Column + "='" + Value + "';";
		try
		{
			Statement statement = Connection.createStatement();
			statement.executeUpdate(Query);
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
		}
	}

	public ResultSet getRowsByColumn(String Table, String Column, String Value)
	{
		ResultSet rs = this.executeQuery("SELECT * FROM " + Table + " WHERE " + Column + "= '" + Value + "';");
		return rs;
	}

	public String getValue(ResultSet Set, String Column)
	{
		try
		{
			return Set.getString(Column);
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets a rowcount in the Table defined by the Table param
	 * 
	 * @param Table
	 *            Table to get the row-count of
	 * @return Count of rows in Table; 0 Means there's either 0 rows, or an
	 *         error occured (Stack trace will be printed if error happens)
	 */
	public int getRowCount(String Table)
	{
		ResultSet rs = this.executeQuery("SELECT * FROM " + Table);
		int count = 0;
		try
		{
			while (rs.next())
			{
				count++;
			}
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
		}
		return count;
	}

	public Connection getConnection()
	{
		return Connection;
	}

	public boolean getBoolean(ResultSet SQL, String Field)
	{
		try
		{
			if (!SQL.isClosed())
			{
				if (SQL.next())
				{
					boolean Return = SQL.getBoolean(Field);
					SQL.close();
					return Return;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
			return false;
		}
	}

	public Object getData(ResultSet SQL, String Column)
	{
		try
		{
			if (!SQL.isClosed())
			{
				if (SQL.next())
				{
					Object Return = SQL.getObject(Column);
					SQL.close();
					return Return;
				}
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}
		catch (SQLException Ex)
		{
			Ex.printStackTrace();
			return null;
		}
	}

	public enum Field
	{
		Boolean, Long, Double, String
	}

}