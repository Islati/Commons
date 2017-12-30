package com.caved_in.commons.game.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface IUserManagerHandler extends Listener {
    /**
     * Handle the operations to be performed when the user is joining the server.
     * This is generally a good place to load player data, add them to the user manager, etc.
     * @param player player who is joining the server.
     */
    void handleJoin(Player player);

    /**
     * Handle the operations to be performed when the user is leaving the server.
     * This is generally a good place to save the player data, and remove them from the user manager.
     * @param player Player who is leaving the server.
     */
    void handleLeave(Player player);

    @EventHandler(priority = EventPriority.HIGHEST)
    default void onPlayerJoin(PlayerJoinEvent e) {
        handleJoin(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void onPlayerQuit(PlayerQuitEvent e) {
        handleLeave(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    default void onPlayerKick(PlayerKickEvent e) {
        handleLeave(e.getPlayer());
    }
}
