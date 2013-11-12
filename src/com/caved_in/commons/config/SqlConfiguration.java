package com.caved_in.commons.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class SqlConfiguration
{
	@Element(name = "MySqlHost")
	private String mySqlHost = "localhost";

	@Element(name = "MySqlPort")
	private String mySqlPort = "3306";

	@Element(name = "Database")
	private String mySqlDatabaseName = "database";

	@Element(name = "MySqlUsername")
	private String mySqlUsername = "username";

	@Element(name = "MySqlPassword")
	private String mySqlPassword = "password";

	public SqlConfiguration(@Element(name = "MySqlHost") String mySqlHost, @Element(name = "MySqlPort") String mySqlPort, @Element(name = "Database") String mySqlDatabase,
			@Element(name = "MySqlUsername") String mySqlUsername, @Element(name = "MySqlPassword") String mySqlPassword)
	{
		this.mySqlHost = mySqlHost;
		this.mySqlPort = mySqlPort;
		this.mySqlDatabaseName = mySqlDatabase;
		this.mySqlUsername = mySqlUsername;
		this.mySqlPassword = mySqlPassword;
	}

	public SqlConfiguration()
	{
	}

	public String getHost()
	{
		return this.mySqlHost;
	}

	public String getPort()
	{
		return this.mySqlPort;
	}

	public String getDatabase()
	{
		return this.mySqlDatabaseName;
	}

	public String getUsername()
	{
		return this.mySqlUsername;
	}

	public String getPassword()
	{
		return this.mySqlPassword;
	}
}
