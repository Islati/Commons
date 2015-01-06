package com.caved_in.commons.game.listener;

import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.player.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameConnectionListener implements Listener {
    private CraftGame game;
    private UserManager userManager;

    public GameConnectionListener(CraftGame game) {
        this.game = game;
        userManager = game.getUserManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        userManager.addUser(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        User user = userManager.getUser(e.getPlayer());
        user.destroy();

        userManager.removeUser(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKick(PlayerKickEvent e) {
        User user = userManager.getUser(e.getPlayer());
        user.destroy();

        userManager.removeUser(e.getPlayer());
    }
}
