package com.caved_in.commons.network;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;

public class Bungee implements PluginMessageListener {

    private static Bungee instance = null;

    private Server server;

    public static Bungee getInstance() {
        if (instance == null) {
            instance = new Bungee();
        }

        return instance;
    }

    private String ip;
    private int port;
    private int playerCount;
    private String[] players;
    private String[] servers;
    private String location;
    private String uuid;
    private String sIp;
    private short sPort;

    protected Bungee() {
        server = Bukkit.getServer();
    }

    /**
     * Send the player to the given bungeecord server. No verification is in place
     * to assure the server exists, and so forth; That's to be done if desired by you.
     *
     * @param player     player you're sending to another server
     * @param serverName name of the server to send them to.
     */
    public void send(Player player, String serverName) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("Connect");
        output.writeUTF(serverName);

        player.sendPluginMessage(Commons.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Send all the player on the server this is ran from to a specific server.
     *
     * @param serverName the name of the bungee server to send all the players to
     */
    public void sendAllToServer(String serverName) {
        Players.stream().forEach(p -> send(p, serverName));
    }

    /**
     * Send the collection of players to the bungee server specified.
     *
     * @param players    players we're moving to another server
     * @param serverName the server that we want the players to go to
     */
    public void sendAllToServer(Collection<Player> players, String serverName) {
        players.stream().forEach(p -> send(p, serverName));
    }

    public String getServerIp(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ServerIP");
        out.writeUTF(server);
        this.server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());

        try {
            wait();
        } catch (InterruptedException e) {
            return String.format("%s:%s", sIp, sPort);
        }

        return String.format("%s:%s", sIp, sPort);
    }

    public void kickPlayer(String name, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(name);
        out.writeUTF(message);
        server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
    }

    public String getUUID(String name) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("UUID");
        server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
        try {
            wait();
        } catch (InterruptedException e) {
            return uuid;
        }
        return uuid;
    }

    public String getServer(String name) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
        try {
            wait();
        } catch (InterruptedException e) {
            return location;
        }
        return location;
    }

    public String[] getServers() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
        try {
            wait();
        } catch (InterruptedException e) {
            return servers;
        }
        return servers;
    }

    public String[] getPlayers(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerList");
        out.writeUTF(server);
        this.server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
        try {
            wait();
        } catch (InterruptedException e) {
            return players;
        }
        return players;
    }

    public int getPlayerCount(String srv) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(srv);
        server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
        try {
            wait();
        } catch (InterruptedException e) {
            return playerCount;
        }
        return playerCount;
    }

    public void message(String name, String msg) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(name);
        out.writeUTF(msg);
        server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
    }

    public String getIP(Player p) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("IP");
        p.sendPluginMessage(Commons.getInstance(), "BungeeCord", output.toByteArray());
        try {
            wait();
        } catch (InterruptedException e) {
            return String.format("%s:%s", ip, port);
        }
        return String.format("%s:%s", ip, port);
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!"BungeeCord".equalsIgnoreCase(s)) {
            return;
        }

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            String subChannel = in.readUTF();

            //todo potentially create a single-void interface to handle custom actions for messages received? For now emulate simple bungee

            //todo create a type translator to retrieve specific data from the DataInputStream, kinda like object translation for commands!
            switch (subChannel) {
                case "IP":
                    ip = in.readUTF();
                    port = in.readInt();
                    break;
                case "PlayerCount":
                    playerCount = in.readInt();
                    break;
                case "PlayerList":
                    players = in.readUTF().split(",");
                    break;
                case "GetServers":
                    servers = in.readUTF().split(",");
                    break;
                case "GetServer":
                    location = in.readUTF();
                    break;
                case "UUID":
                    uuid = in.readUTF();
                    break;
                case "ServerIP":
                    sIp = in.readUTF();
                    sPort = in.readShort();
                    break;
                default:
                    break;
            }
            notifyAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
