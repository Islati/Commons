package com.caved_in.commons.game.listener;

import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.player.User;
import com.caved_in.commons.plugin.BukkitPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserManagerListener implements Listener {
    private BukkitPlugin parent;
    private UserManager userManager;

    public UserManagerListener(UserManager manager) {
        this.userManager = manager;
    }

    public UserManagerListener(CraftGame game) {
        parent = game;
        userManager = game.getUserManager();
    }

    protected UserManager getUserManager() {
        return userManager;
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
        if (user == null) {
            parent.getPluginLogger().severe("Unable to retrieve User data (" + userManager.getUserClass().getCanonicalName() + ") for player " + e.getPlayer().getName());
            return;
        }

        user.destroy();

        userManager.removeUser(e.getPlayer());
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent e) {
        UserManager manager = getUserManager();
        try {
            manager.getUser(e.getPlayer()).updateWorld();
            parent.getPluginLogger().info("Updated world for " + e.getPlayer().getName());
        } catch (NullPointerException ex) {
            parent.getThreadManager().runTaskLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        manager.getUser(e.getPlayer()).updateWorld();
                    } catch (Exception ex1) {
                        //todo
                    }
                }
            }, 40);
        }
    }
}
