package com.caved_in.commons.game.listener;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.MiniGame;
import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.player.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameConnectionListener implements Listener {
    private MiniGame game;
    private UserManager userManager;

    public GameConnectionListener(MiniGame game) {
        this.game = game;
        userManager = game.getUserManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        userManager.addUser(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        User user = userManager.getUser(e.getPlayer());
        user.destroy();

        userManager.removeUser(e.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        User user = userManager.getUser(e.getPlayer());
        user.destroy();

        userManager.removeUser(e.getPlayer());
    }
}
