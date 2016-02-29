package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.base.Config;

public class Servers extends Config {
    /**
     * Should we display the player count.
     */
    private boolean displayPlayers;
    /**
     * Should we display the motd.
     */
    private boolean displayMotd;
    /**
     * The name of the server that matches BungeeCord.
     */
    private String server;
    /**
     * The host name of the server.
     */
    private String hostName;
    /**
     * The port of this server.
     */
    private int port;
    /**
     * The timeout of the server.
     */
    private Integer timeout;

    public void setDisplayPlayers(boolean displayPlayers) {
        this.displayPlayers = displayPlayers;
    }

    public void setDisplayMotd(boolean displayMotd) {
        this.displayMotd = displayMotd;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}


