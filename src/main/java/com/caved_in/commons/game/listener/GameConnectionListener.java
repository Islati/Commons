package com.caved_in.commons.game.listener;

import com.caved_in.commons.game.MiniGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameConnectionListener implements Listener {
    private MiniGame game;

    public GameConnectionListener(MiniGame game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {

    }
}
