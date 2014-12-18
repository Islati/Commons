package com.caved_in.commons.game.players;

import com.caved_in.commons.player.User;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface IUserManager<T extends User> {
    public void addUser(Player p);

    public T getUser(Player p);

    public T getUser(UUID id);

    public void removeUser(Player p);

    public void removeUser(UUID id);
}
