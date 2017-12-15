package com.caved_in.commons.nms;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.bukkit.entity.Player;
import java.lang.reflect.Method;

public class NmsPlayers {

    /**
     * Sends the packet to the players connection.
     *
     * @param player player to send the packet to
     * @param packet packet to send to the player
     */
    public static void sendPacket(Player player, Object packet) {
        Method sendPacket = ReflectionUtilities.getMethod(ReflectionUtilities.getNMSClass("PlayerConnection"), "sendPacket", ReflectionUtilities.getNMSClass
                ("Packet"));
        Object playerConnection = getConnection(player);

        try {
            sendPacket.invoke(playerConnection, packet);
        } catch (Exception e) {
            Chat.debug("Failed to send a packet to: " + player.getName());
            e.printStackTrace();
        }
    }

    /**
     * Get the EntityPlayer from a player
     *
     * @param player player to get the EntityPlayer handle of
     * @return EntityPlayer handle for the player object
     */
    public static Object toEntityPlayer(Player player) {
        Method getHandle = ReflectionUtilities.getMethod(player.getClass(), "getHandle");
        try {
            return getHandle.invoke(player);
        } catch (Exception e) {
            Chat.debug("Failed retrieve the NMS Player-Object of:" + player.getName());
            return null;
        }
    }

    /**
     * Get the connection instance for the player object
     *
     * @param player player to get the connection for
     * @return connection for the player, or null if none exists
     */
    public static Object getConnection(Player player) {
        return ReflectionUtilities.getField(ReflectionUtilities.getNMSClass("EntityPlayer"), "playerConnection", toEntityPlayer(player));
    }

    /**
     * Get the network manager for a player
     *
     * @param player player to get the network manager of
     * @return network manager object for the player, or null if unable to retrieve
     */
    public static Object getNetworkManager(Player player) {
        try {
            return ReflectionUtilities.getField(getConnection(player).getClass(), "networkManager").get(getConnection(player));
        } catch (IllegalAccessException e) {
            Chat.debug("Failed to get the NetworkManager of player: " + player.getName());
            return null;
        }
    }

    public static void setContainerDefault(Player player) {

    }

    public static void setActiveContainer(Player player, int id) {

    }

    public void closeInventory(Player player) {

    }


}
