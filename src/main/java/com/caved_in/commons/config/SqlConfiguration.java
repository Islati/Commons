package com.caved_in.commons.config;

import com.caved_in.commons.yml.YamlConfig;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class SqlConfiguration extends YamlConfig {
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

    @Element(name = "track-online-status", required = false)
    private boolean trackPlayerOnlineStatus = false;

    public SqlConfiguration(@Element(name = "MySqlHost") String mySqlHost, @Element(name = "MySqlPort") String mySqlPort, @Element(name = "Database") String mySqlDatabase,
                            @Element(name = "MySqlUsername") String mySqlUsername, @Element(name = "MySqlPassword") String mySqlPassword, @Element(name = "track-online-status", required = false) boolean trackOnlineStatus) {
        this.mySqlHost = mySqlHost;
        this.mySqlPort = mySqlPort;
        this.mySqlDatabaseName = mySqlDatabase;
        this.mySqlUsername = mySqlUsername;
        this.mySqlPassword = mySqlPassword;
        this.trackPlayerOnlineStatus = trackOnlineStatus;
    }

    public SqlConfiguration() {
    }

    public String getHost() {
        return this.mySqlHost;
    }

    public String getPort() {
        return this.mySqlPort;
    }

    public String getDatabase() {
        return this.mySqlDatabaseName;
    }

    public String getUsername() {
        return this.mySqlUsername;
    }

    public String getPassword() {
        return this.mySqlPassword;
    }

    public boolean trackPlayerOnlineStatus() {
        return trackPlayerOnlineStatus;
    }

    public void setMySqlHost(String mySqlHost) {
        this.mySqlHost = mySqlHost;
    }

    public void setMySqlPort(String mySqlPort) {
        this.mySqlPort = mySqlPort;
    }

    public void setMySqlDatabaseName(String mySqlDatabaseName) {
        this.mySqlDatabaseName = mySqlDatabaseName;
    }

    public void setMySqlUsername(String mySqlUsername) {
        this.mySqlUsername = mySqlUsername;
    }

    public void setMySqlPassword(String mySqlPassword) {
        this.mySqlPassword = mySqlPassword;
    }

    public void setTrackPlayerOnlineStatus(boolean trackPlayerOnlineStatus) {
        this.trackPlayerOnlineStatus = trackPlayerOnlineStatus;
    }
}
