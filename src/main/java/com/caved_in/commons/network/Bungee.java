package com.caved_in.commons.network;

import com.caved_in.commons.Commons;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

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
}
