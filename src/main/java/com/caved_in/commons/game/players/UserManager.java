package com.caved_in.commons.game.players;

import com.caved_in.commons.player.User;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager<T extends User> implements IUserManager<T> {
    private Map<UUID, T> users = new HashMap<>();

    /* The class that the user will be created from. */
    private Class<? extends User> playerClass;

    /* Constructor for the user class */
    private Constructor userContructor;

    public UserManager(Class<? extends User> userClass) {
        this.playerClass = userClass;

        /* Get the player-constructor for the user class */
        userContructor = ReflectionUtilities.getConstructor(playerClass, Player.class);
    }

    public void addUser(Player p) {
        /* Create the user object from the polled constructor, and the player object */
        T userObject = ReflectionUtilities.invokeConstructor(userContructor, p);

        /* Put the newly created user object into the map of users! */
        users.put(p.getUniqueId(), userObject);
    }

    public void disposeAll() {
        for (T user : users.values()) {
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
        users.remove(id);
    }

    public Class<? extends User> getUserClass() {
        return playerClass;
    }

    public Collection<T> allUsers() {
        return users.values();
    }
}
