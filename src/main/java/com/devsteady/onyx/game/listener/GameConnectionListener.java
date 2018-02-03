package com.devsteady.onyx.game.listener;

import com.devsteady.onyx.game.CraftGame;
import com.devsteady.onyx.game.players.UserManager;
import com.devsteady.onyx.player.User;
import com.devsteady.onyx.plugin.BukkitPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Deprecated
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
        user.destroy();

        userManager.removeUser(e.getPlayer());
    }
}
