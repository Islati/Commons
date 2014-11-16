package com.caved_in.commons.game.players;

import com.caved_in.commons.player.User;
import org.bukkit.entity.Player;

public interface UserManager<T extends User> {
    public void addUser(Player p);

    public T getUser(Player p);

    public void removeUser(Player p);
}
