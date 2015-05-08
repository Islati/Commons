package com.caved_in.commons.player;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.world.Worlds;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;

import java.util.UUID;

public abstract class User implements PlayerWrapper {
    @Element(name = "name")
    private String name;

    private UUID id;

    @Element(name = "uuid")
    private String uid;

    @Element(name = "world")
    private String worldName;

    public User(Player p) {
        name = p.getName();
        id = p.getUniqueId();
        uid = id.toString();
        worldName = p.getWorld().getName();
    }

    public User(@Element(name = "name") String name, @Element(name = "uuid") String uid, @Element(name = "world") String world) {
        this.name = name;
        this.uid = uid;
        id = UUID.fromString(this.uid);
        this.worldName = world;
    }

    public User() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
        uid = id.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    /**
     * Retrieve the {@link org.bukkit.entity.Player} for the wrapped user object.
     */
    public Player getPlayer() {
        return Players.getPlayer(id);
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

    public void destroy() {

    }

    /**
     * @return the name of the world the player is currently in.
     */
    public String getWorldName() {
        return worldName;
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
}
