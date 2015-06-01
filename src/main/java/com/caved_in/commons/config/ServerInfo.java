package com.caved_in.commons.config;

/**
 * Manufactured in the {@link com.caved_in.commons.sql.ServerDatabaseConnector} inside Commons
 * to retrieve information about servers on the network (assuming*).
 */
public class ServerInfo implements Comparable<ServerInfo> {

    private String name;
    private int playerCount;
    private int maxPlayerCount;
    private String ip;
    private int port;
    private String[] players;
    private boolean online = false;

    public ServerInfo() {

    }

    public ServerInfo(String name, int playerCount, int maxPlayerCount) {
        this.name = name;
        this.playerCount = playerCount;
        this.maxPlayerCount = maxPlayerCount;
    }

    public ServerInfo players(String[] players) {
        this.players = players;
        return this;
    }

    public ServerInfo port(int port) {
        this.port = port;
        return this;
    }

    public ServerInfo ip(String ip) {
        this.ip = ip;
        return this;
    }

    public ServerInfo name(String name) {
        setName(name);
        return this;
    }

    public ServerInfo players(int count) {
        setPlayerCount(count);
        return this;
    }

    public ServerInfo maxPlayers(int count) {
        setMaxPlayerCount(count);
        return this;
    }

    public ServerInfo online(boolean isOnline) {
        setOnline(isOnline);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String[] getPlayers() {
        return players;
    }

    @Override
    public int compareTo(ServerInfo o) {
        return Integer.compare(getPlayerCount(), o.getPlayerCount());
    }
}
