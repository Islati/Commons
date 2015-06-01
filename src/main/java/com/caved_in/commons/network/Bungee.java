package com.caved_in.commons.network;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.ServerInfo;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//todo make this mother fucking piece of shit class work.
public class Bungee implements PluginMessageListener {

    private static Bungee instance = null;

    public static Bungee getInstance() {
        if (instance == null) {
            instance = new Bungee();
        }

        return instance;
    }

    private Map<String, ServerInfo> serverInfoMap = new HashMap<>();

    private Map<String, String> playerUUIDMap = new HashMap<>();
    //
//    private String ip;
//    private int port;
//    private int playerCount;
//    private String[] players;
    private String[] servers;
//    private String location;
//    private String uuid;
//    private String sIp;
//    private short sPort;

    protected Bungee() {

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
        Bukkit.getServer().sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());

        ServerInfo info = getInfo(server);

        return String.format("%s:%s", info.getIp(), info.getPort());
    }

    public void kickPlayer(String name, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(name);
        out.writeUTF(message);
        Bukkit.getServer().sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
    }

//    public String getUUID(String name) {
//        ByteArrayDataOutput out = ByteStreams.newDataOutput();
//        out.writeUTF("UUID");
//        server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
//
//        //todo implement per-player information!
//        return uuid;
//    }

    /**
     * Get the server on which a player resides.
     *
     * @param name name of the player to get the server for
     * @return //
     */
//    public String getServer(String name) {
//        ByteArrayDataOutput out = ByteStreams.newDataOutput();
//        out.writeUTF("GetServer");
//        server.sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
//        return location;
//    }

    public String[] getServers() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        Bukkit.getServer().sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
        return servers;
    }

    public String[] getPlayers(String server) {
        verifyInformation(server);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerList");
        out.writeUTF(server);
        Bukkit.getServer().sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());

        return getInfo(server).getPlayers();
    }

    public int getPlayerCount(String srv) {
        verifyInformation(srv);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(srv);
        Bukkit.getServer().sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
        Chat.debug("Send playercount plugin message!");
        return getInfo(srv).getPlayerCount();
    }

    public void message(String name, String msg) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(name);
        out.writeUTF(msg);
        Bukkit.getServer().sendPluginMessage(Commons.getInstance(), "BungeeCord", out.toByteArray());
    }
//
//    public String getIP(Player p) {
//        ByteArrayDataOutput output = ByteStreams.newDataOutput();
//        output.writeUTF("IP");
//        p.sendPluginMessage(Commons.getInstance(), "BungeeCord", output.toByteArray());
//        return String.format("%s:%s", ip, port);
//    }

    public ServerInfo getInfo(String serverName) {
        verifyInformation(serverName);

        return serverInfoMap.get(serverName);
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        Chat.debug("onPluginMessage: s = " + s);
        if (!"BungeeCord".equalsIgnoreCase(s)) {
            return;
        }

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            String subChannel = in.readUTF();
            Chat.debug("Sub Channel is " + subChannel);

            //todo potentially create a single-void interface to handle custom actions for messages received? For now emulate simple bungee

            //todo create a type translator to retrieve specific data from the DataInputStream, kinda like object translation for commands!
            switch (subChannel) {
                case "IP":
//                    ip = in.readUTF();
//                    port = in.readInt();
                    break;
                case "PlayerCount":
                    String countSrvName = in.readUTF();
                    Chat.broadcast("Recieved server " + countSrvName + " for player count");
                    verifyInformation(countSrvName);
                    getInfo(countSrvName).setPlayerCount(in.readInt());

                    Chat.broadcast("Has " + getInfo(countSrvName).getPlayerCount());
                    break;
                case "PlayerList":
                    String plSrvName = in.readUTF();
                    verifyInformation(plSrvName);
                    getInfo(plSrvName).players(in.readUTF().split(","));
                    Chat.debug("Got players for " + plSrvName + ": " + StringUtil.joinString(getInfo(plSrvName).getPlayers(),", "));
                    break;
                case "GetServers":
                    servers = in.readUTF().split(",");
                    break;
                case "GetServer":
//                    location = in.readUTF();
                    break;
                case "UUID":
//                    uuid = in.readUTF();
                    break;
                case "ServerIP":
                    String serverIpPortName = in.readUTF();
                    verifyInformation(serverIpPortName);
                    getInfo(serverIpPortName).ip(in.readUTF()).port(in.readShort());
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verifyInformation(String serverName) {
        if (!serverInfoMap.containsKey(serverName)) {
            serverInfoMap.put(serverName, new ServerInfo());
        }
    }
}
