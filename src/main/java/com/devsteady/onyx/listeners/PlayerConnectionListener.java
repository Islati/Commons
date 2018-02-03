package com.devsteady.onyx.listeners;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.permission.Perms;
import com.devsteady.onyx.player.MinecraftPlayer;
import com.devsteady.onyx.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerConnectionListener implements Listener {

    private Onyx onyx;

    public PlayerConnectionListener(Onyx onyx) {
        this.onyx = onyx;
    }

    @EventHandler
    public void onPlayerKicked(PlayerKickEvent event) {
        Player player = event.getPlayer();

        UUID playerId = player.getUniqueId();
        Players.removeData(playerId);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //Reset the players walk and fly speeds
        player.setFlySpeed((float) MinecraftPlayer.DEFAULT_FLY_SPEED);
        player.setWalkSpeed((float) MinecraftPlayer.DEFAULT_WALK_SPEED);
        //Initialize the wrapped player data
        onyx.getPlayerHandler().addData(player);
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        //Remove the cached player instance!
        Players.removeData(playerId);
    }
}
