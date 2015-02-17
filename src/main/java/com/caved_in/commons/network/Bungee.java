package com.caved_in.commons.network;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Bungee {

    /**
     * Send the player to the given bungeecord server. No verification is in place
     * to assure the server exists, and so forth; That's to be done if desired by you.
     *
     * @param player     player you're sending to another server
     * @param serverName name of the server to send them to.
     */
    public static void sendToServer(Player player, String serverName) {
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
    public static void sendAllToServer(String serverName) {
        Players.stream().forEach(p -> sendToServer(p, serverName));
    }

    /**
     * Send the collection of players to the bungee server specified.
     *
     * @param players    players we're moving to another server
     * @param serverName the server that we want the players to go to
     */
    public static void sendAllToServer(Collection<Player> players, String serverName) {
        players.stream().forEach(p -> sendToServer(p, serverName));
    }
}
