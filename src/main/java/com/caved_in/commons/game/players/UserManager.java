package com.caved_in.commons.game.players;

import com.caved_in.commons.game.event.UserJoinEvent;
import com.caved_in.commons.game.event.UserQuitEvent;
import com.caved_in.commons.player.User;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager<T extends User> implements IUserManager<T> {
    private Map<UUID, T> users = new HashMap<>();

    /* The class that the user will be created from. */
    private Class<? extends User> playerClass = null;

    /* Constructor for the user class */
    private Constructor userContructor = null;

    private JavaPlugin parent = null;

    /**
     * Initialize a new user manager and set the derived class of {@link com.caved_in.commons.player.User} which the manager handles.
     *
     * @param userClass the derived class of {@link com.caved_in.commons.player.User} which the manager handles
     */
    public UserManager(Class<? extends User> userClass) {
        setUserClass(userClass);
    }

    /**
     * Initialize a new user manager without registering the user class; Until the
     * user class has been initialized, the user manager will be dysfunctional.
     */
    public UserManager() {

    }

    public void setParent(JavaPlugin plugin) {
        this.parent = plugin;
    }

    public JavaPlugin getParent() {
        return parent;
    }

    public void addUser(Player p) {
        /* Create the user object from the polled constructor, and the player object */
        T userObject = ReflectionUtilities.invokeConstructor(userContructor, p);

        /* Put the newly created user object into the map of users! */
        users.put(p.getUniqueId(), userObject);

        /*
        Create the event for when a user joins, and call it for plugins to listen!
         */
        callUserJoin(userObject);
    }

    public void addUser(T user) {
        users.put(user.getId(), user);
    }

    public void disposeAll() {
        for (T user : users.values()) {
            callUserQuit(user);
            user.destroy();
        }
    }

    public T getUser(Player p) {
        return getUser(p.getUniqueId());
    }

    public T getUser(UUID id) {
        return users.get(id);
    }

    public void removeUser(Player p) {
        removeUser(p.getUniqueId());
    }

    public void removeUser(UUID id) {
        remove(id);
    }

    public boolean hasData(UUID id) {
        return users.containsKey(id);
    }

    protected void put(UUID id, T user) {
        users.put(id, user);
    }

    protected void remove(UUID id) {
        T user = getUser(id);
        callUserQuit(user);
        users.remove(id);
    }

    /**
     * Set the class for users which the usermanager will handle.
     *
     * @param userClass the class deriving from {@link com.caved_in.commons.player.User}, which the usermanager will handle.
     */
    public void setUserClass(Class<? extends User> userClass) {
        this.playerClass = userClass;

        /* Get the player-constructor for the user class */
        userContructor = ReflectionUtilities.getConstructor(playerClass, Player.class);
    }

    /**
     * @return class for the custom user object for which this user manager works with.
     */
    public Class<? extends User> getUserClass() {
        return playerClass;
    }

    /**
     * Check whether or not there's a class for the users specified.
     * Note: If there's no player class, the user manager will not work.
     *
     * @return true if there's a player class registered, false otherwise.
     */
    public boolean hasUserClass() {
        return playerClass != null && userContructor != null;
    }

    /**
     * Retrieve a set of all the user objects who have data loaded and managed.
     *
     * @return a set of all the user objects who have data loaded.
     */
    public Collection<T> allUsers() {
        return users.values();
    }

    /**
     * @return whether or not there's any user data in the user manager.
     */
    public boolean hasUsers() {
        return users.size() > 0;
    }

    protected void callUserJoin(T userObject) {
        /*
        Create the event for when a user joins, and call it for plugins to listen!
         */
        UserJoinEvent userJoinEvent = new UserJoinEvent(getParent(), userObject);
        Plugins.callEvent(userJoinEvent);
    }

    protected void callUserQuit(T userObject) {
        UserQuitEvent userQuitEvent = new UserQuitEvent(getParent(), userObject);
        Plugins.callEvent(userQuitEvent);
    }
}
