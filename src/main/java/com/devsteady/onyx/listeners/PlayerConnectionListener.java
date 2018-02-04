package com.devsteady.onyx.listeners;

import com.devsteady.onyx.player.OnyxPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //Reset the players walk and fly speeds
        player.setFlySpeed((float) OnyxPlayer.DEFAULT_FLY_SPEED);
        player.setWalkSpeed((float) OnyxPlayer.DEFAULT_WALK_SPEED);
    }
}
