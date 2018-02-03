package com.devsteady.onyx.game.listener;

import com.devsteady.onyx.game.players.UserManager;
import com.devsteady.onyx.plugin.BukkitPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class AbstractUserManagerListener implements IUserManagerHandler {
    private UserManager userManager = null;
    private BukkitPlugin plugin = null;

    public AbstractUserManagerListener(BukkitPlugin plugin, UserManager userManager) {
        this.plugin = plugin;
        this.userManager = userManager;
    }

    public abstract void handleJoin(Player player);
    public abstract void handleLeave(Player player);

    protected BukkitPlugin getPlugin() {
        return plugin;
    }

    protected UserManager getUserManager() {
        return userManager;
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent e) {
        handleJoin(e.getPlayer());
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent e) {
        handleLeave(e.getPlayer());
    }

    @Override
    public void onPlayerKick(PlayerKickEvent e) {
        handleLeave(e.getPlayer());
    }

    /**
     * Used to update the player data with their active world, after switching worlds, by default.
     * If you override this event handler with your own functionally, simply be aware without updating the players
     * world any functionality
     */
    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent e) {
        try {
            userManager.getUser(e.getPlayer()).updateWorld();
            plugin.getPluginLogger().info("Updated world for " + e.getPlayer().getName());
        } catch (NullPointerException ex) {
            plugin.getThreadManager().runTaskLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        userManager.getUser(e.getPlayer()).updateWorld();
                    } catch (Exception ex1) {
                        //todo
                    }
                }
            }, 40);
        }
    }
}
