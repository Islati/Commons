package com.caved_in.commons.effect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class PlayerGlowRed {

    //Required for reflection
    private static Constructor<?> packetPlayOutAnimation;
    private static Method getHandle;
    private static Field playerConnection;
    private static Method sendPacket;

    static {
        try {
            //get the constructor of the packet
            packetPlayOutAnimation = getMCClass("PacketPlayOutAnimation").getConstructor(getMCClass("Entity"), int.class);
            //get method for recieving craftplayer's entityplayer
            getHandle = getCraftClass("entity.CraftPlayer").getMethod("getHandle");
            //get the playerconnection of the entityplayer
            playerConnection = getMCClass("EntityPlayer").getDeclaredField("playerConnection");
            //method to send the packet
            sendPacket = getMCClass("PlayerConnection").getMethod("sendPacket", getMCClass("Packet"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JavaPlugin plugin;
    private Set<String> players = new HashSet<>();
    private int packetRange = 50;
    private BukkitRunnable effectThread = null;

    public PlayerGlowRed(JavaPlugin plugin) {
        this(plugin, true);
    }

    public PlayerGlowRed(JavaPlugin plugin, boolean start) {
        this.plugin = plugin;
        if (start) {
            start();
        }
    }

    /**
     * Adds the red glowing effect to the player
     *
     * @param name name of the player to add the effect to
     */
    public void addRed(String name) {
        players.add(name);
    }


    /**
     * Adds the red glowing effect to the player
     *
     * @param p player to add the effect to
     * @see #addRed(String)
     */
    public void addRed(Player p) {
        addRed(p.getName());
    }

    /**
     * Removes the effect from the player
     *
     * @param name name of the player to remove the effect from
     * @return true if the player had the effect removed, false if they didn't have the effect
     */
    public boolean removeRed(String name) {
        return players.remove(name);
    }

    /**
     * Removes the effect from the player
     *
     * @param p player to remove the effect from
     * @return true if the player had the effect removed, false if they didn't have the effect
     * @see #removeRed(String)
     */
    public boolean removeRed(Player p) {
        return removeRed(p.getName());
    }

    /**
     * Check whether or not a player has the glowing effect
     *
     * @param p player to check the effect for
     * @return true if the player has the effect, false otherwises
     */
    public boolean isRed(Player p) {
        return players.contains(p.getName());
    }

    /**
     * Check whether or not a player has the glowing effect
     *
     * @param name name of the player player to check the effect for
     * @return true if the player has the effect, false if they don't have the effect, or no data for the player was found
     */
    public boolean isRed(String name) {
        return players.contains(name);
    }

    /**
     * Used to start playing the effect
     */
    public void start() {
        if (effectThread == null) {
            effectThread = new BukkitRunnable() {
                @Override
                public void run() {
                    for (String name : players) {
                        try {
                            Player p = plugin.getServer().getPlayerExact(name);

                            if (p == null) {
                                continue;
                            }

                            Object nms_entity = getHandle.invoke(p);
                            // create a new packet instance
                            Object packet = packetPlayOutAnimation.newInstance(nms_entity, 1);

                            // loop through all players in the same world
                            for (Player pl : p.getWorld().getPlayers()) {
                                //Assure we're not sending the packet to the player themself

                                if (pl.getName().equals(p.getName())) {
                                    continue;
                                }

                                if (pl.getLocation().distance(p.getLocation()) > packetRange) {
                                    continue;
                                }

                                Object nms_player = getHandle.invoke(pl);
                                // get the players connection
                                Object nms_connection = playerConnection.get(nms_player);
                                // send the packet
                                sendPacket.invoke(nms_connection, packet);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }
        effectThread.runTaskTimer(this.plugin, -1, 20);
    }

    /**
     * Used to retrieve Minecraft classes with a specific name
     *
     * @param name name of the class to retrieve
     * @return Class of the name requested if it was able to be found
     * @throws ClassNotFoundException
     */
    private static Class<?> getMCClass(String name) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "net.minecraft.server." + version + name;
        return Class.forName(className);
    }

    /**
     * Used to retrieve CraftBukkit classes with a specific name
     *
     * @param name name of the class to retrieve
     * @return Class of the name requested if it was able to be found
     * @throws ClassNotFoundException
     */
    private static Class<?> getCraftClass(String name) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "org.bukkit.craftbukkit." + version + name;
        return Class.forName(className);
    }

    public int getPacketRange() {
        return packetRange;
    }

    public void setPacketRange(int packetRange) {
        this.packetRange = packetRange;
    }
}