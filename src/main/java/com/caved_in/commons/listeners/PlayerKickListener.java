package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.UpdateOnlineStatusThread;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.UUID;

public class PlayerKickListener implements Listener {

    private WorldConfiguration config;

    public PlayerKickListener() {
        config = Commons.getInstance().getConfiguration().getWorldConfig();
    }

    @EventHandler
    public void onPlayerKicked(PlayerKickEvent event) {
        if (!config.hasJoinMessages()) {
            event.setLeaveMessage(null);
        }

        Player player = event.getPlayer();

        if (Players.hasPermission(player, Perms.KICK_DENY)) {
            event.setCancelled(true);
            return;
        }


        UUID playerId = player.getUniqueId();

        //Update the player's online status in the database
        Commons.getInstance().getThreadManager().runTaskAsync(new UpdateOnlineStatusThread(playerId, false));

        Players.removeData(playerId);
//		if (Commons.hasSqlBackend()) {
//			Commons.disguiseDatabase.deletePlayerDisguiseData(playerName);
//		}
    }
}
