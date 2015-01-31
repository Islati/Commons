package com.caved_in.commons.game.listener;

import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.player.User;
import com.caved_in.commons.plugin.BukkitPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameConnectionListener implements Listener {
    private BukkitPlugin parent;
    private UserManager userManager;

    public GameConnectionListener(UserManager manager) {
        this.userManager = manager;
    }

    public GameConnectionListener(CraftGame game) {
        this.parent = game;
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
