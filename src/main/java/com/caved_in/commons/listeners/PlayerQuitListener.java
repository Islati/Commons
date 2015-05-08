package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.UpdateOnlineStatusThread;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {
    private WorldConfiguration config;

    private static Commons commons = Commons.getInstance();

    public PlayerQuitListener() {
        config = Commons.getInstance().getConfiguration().getWorldConfig();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        //If there's no leave/join messages, then remove the message!
        if (!config.hasJoinMessages()) {
            event.setQuitMessage(null);
        }

        //Remove the cached player instance!
        Players.removeData(playerId);

        if (!commons.hasDatabaseBackend()) {
            return;
        }

        //Change the player's online status.
        commons.getThreadManager().runTaskAsync(new UpdateOnlineStatusThread(playerId, false));

//
//		if (Commons.hasSqlBackend()) {
//			Commons.disguiseDatabase.deletePlayerDisguiseData(playerId);
//		}

        commons.getThreadManager().runTaskLaterAsync(() -> {
            commons.getServerDatabase().updatePlayerCount();
        }, 20);
    }
}
