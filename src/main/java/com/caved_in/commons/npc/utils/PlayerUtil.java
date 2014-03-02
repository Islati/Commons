package com.caved_in.commons.npc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.util.io.netty.channel.Channel;

import org.bukkit.entity.Player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.npc.reflection.ReflectionUtil;

public class PlayerUtil {

    private static final Field channelField = ReflectionUtil.getField(ReflectionUtil.getNMSClass("NetworkManager"), "k");

    public static void sendPacket(Player player, Object packet){
        Method sendPacket = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("PlayerConnection"), "sendPacket", ReflectionUtil.getNMSClass("Packet"));
        Object playerConnection = getPlayerConnection(player);

        try {
            sendPacket.invoke(playerConnection, packet);
        } catch (Exception e) {
            Commons.messageConsole("Failed to send a packet to: " + player.getName());
            e.printStackTrace();
        }
    }

    public static Object playerToEntityPlayer(Player player){
        Method getHandle = ReflectionUtil.getMethod(player.getClass(), "getHandle");
        try {
            return getHandle.invoke(player);
        } catch (Exception e) {
            Commons.messageConsole("Failed retrieve the NMS Player-Object of:" + player.getName());
            return null;
        }
    }

    public static Object getPlayerConnection(Player player){
        Object connection = ReflectionUtil.getField(ReflectionUtil.getNMSClass("EntityPlayer"), "playerConnection", playerToEntityPlayer(player));
        return connection;
    }

    public static Object getNetworkManager(Player player) {
        try {
            return ReflectionUtil.getField(getPlayerConnection(player).getClass(), "networkManager").get(getPlayerConnection(player));
        } catch (IllegalAccessException e) {
            Commons.messageConsole("Failed to get the NetworkManager of player: " + player.getName());
            return null;
        }
    }

    public static Channel getChannel(Player player) {
        try {
            return (Channel) channelField.get(getNetworkManager(player));
        } catch (IllegalAccessException e) {
            Commons.messageConsole("Failed to get the channel of player: " + player.getName());
            return null;
        }
    }
}
