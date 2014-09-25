package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.UpdateOnlineStatusThread;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		UUID playerId = event.getPlayer().getUniqueId();

		//If there's no leave/join messages, then remove the message!
		if (!Commons.getConfiguration().getWorldConfig().hasJoinMessages()) {
			event.setQuitMessage(null);
		}

		//Change the player's online status.
		Commons.getInstance().getThreadManager().runTaskAsync(new UpdateOnlineStatusThread(playerId, false));

		//Remove the cached player instance!
		Players.removeData(playerId);
//
//		if (Commons.hasSqlBackend()) {
//			Commons.disguiseDatabase.deletePlayerDisguiseData(playerId);
//		}
	}
}
