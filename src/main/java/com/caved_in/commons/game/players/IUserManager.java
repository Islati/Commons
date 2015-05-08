package com.caved_in.commons.game.players;

import com.caved_in.commons.player.User;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

/**
 * Template used for implementing a UserManager.
 *
 * @param <T> Extension of {@link User} used to handle data related to each player, individually.
 */
public interface IUserManager<T extends User> {
    /**
     * Add a user to the User Manager.
     * Automatically creates or loads the data for the given player.
     * @param p player to add / load / create data for.
     */
    void addUser(Player p);

    /**
     * Retrieve data for the given player.
     * @param p player to retrieve the data for.
     * @return data for the given player.
     */
    T getUser(Player p);

    /**
     *
     * @param id
     * @return
     */
    T getUser(UUID id);

    void removeUser(Player p);

    void removeUser(UUID id);

    Collection<T> allUsers();
}
