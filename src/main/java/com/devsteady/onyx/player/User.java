package com.devsteady.onyx.player;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.world.Worlds;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.YamlConfig;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class User extends YamlConfig implements PlayerWrapper {
    @Path("name")
    private String name;

    @Path("uuid")
    private String id;

    @Path("world")
    private String worldName;

    public User(Player p) {
        name = p.getName();
        id = p.getUniqueId().toString();
        worldName = p.getWorld().getName();
    }

    public User(String name, UUID id, String world) {
        this.name = name;
        this.id = id.toString();
        this.worldName = world;
    }

    public User() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getId() {
        return UUID.fromString(id);
    }

    @Override
    /**
     * Retrieve the {@link org.bukkit.entity.Player} for the wrapped user object.
     */
    public Player getPlayer() {
        return Players.getPlayer(UUID.fromString(id));
    }

    /**
     * Whether or not the player is online
     *
     * @return true if the player is online, false otherwise
     */
    public boolean isOnline() {
        return Players.isOnline(getId());
    }

    public World getWorld() {
        return Worlds.getWorld(worldName);
    }

    /**
     * Updates the cached world variable to players existing world. Used internally.
     */
    @Deprecated
    public void updateWorld() {
        World playerWorld = getPlayer().getWorld();
        String playerWorldName = playerWorld.getName();

		/*
        If the world the player is switching to is the same as their existing world
		(player isn't actually changing worlds)
		then quit while we're ahead.
		 */
        if (worldName != null && playerWorldName.equals(worldName)) {
            return;
        }

        World oldWorld = Worlds.getWorld(worldName);
        worldName = playerWorld.getName();
        onWorldChange(oldWorld, playerWorld);
    }

    /**
     * Synchronize the user instances data to the player given.
     * @param player Player to update user data with.
     */
    public void sync(Player player) {
        this.id = player.getUniqueId().toString();
        this.name = player.getName();
        this.worldName = player.getWorld().getName();
    }

    /**
     * To be called when the player leaves the server.
     */
    public void destroy() {

    }

    /**
     * @return the name of the world the player is currently in.
     */
    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    /**
     * Called whenever the user changes worlds; Has no behaviour by default, though it can be overridden to fulfill your needs.
     *
     * @param from the world the player switched from.
     * @param to   the world the player has switched to.
     */
    public void onWorldChange(World from, World to) {

    }

    /**
     * Send the player a message to their chat box.
     *
     * @param messages message(s) to send.
     */
    public void message(String... messages) {
        Chat.message(getPlayer(), messages);
    }

    /**
     * Send the player a message to their action bar!
     *
     * @param message message to be shown.
     */
    public void actionMessage(String message) {
        Chat.actionMessage(getPlayer(), message);
    }

    /**
     * Send the player a formatted chat message!
     *
     * @param msg  message to be formatted with args and shown to the player.
     * @param args arguments to format the message with
     */
    public void format(String msg, Object... args) {
        Chat.format(getPlayer(), msg, args);
    }
    @Override
    public String toString() {
        return getName();
    }
}
